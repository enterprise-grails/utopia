package com.utopia

import grails.converters.JSON

class AsyncProcessService {

	static transactional = false

    static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true
    static container = "async"
    static selector = "eventName in ('NewSimpleMerchOrderAsync', 'UpdatedSimpleMerchOrderAsync') OR referenceId LIKE '/order/%/simple-merch-a/%'"

    def eventService
    def orderService

	def begin(order) {
        if (validate(order)) {
    		bill(order)
	    }
	}
    
    def validate(order) {
        def errors = [:]
        if (!order.cardNumber) errors << [cardNumber: "Missing card number"]
        if (!order.expiration) errors << [expiration: "Missing card expiration"]
        if (errors) {
            eventService.publish(
                eventName: "OrderValidationException",
                referenceId: getSelfRef(order.id),
                details: errors
            )
        }
        return (errors.size() == 0)
    }

	def bill(order) {
        eventService.publish(
            eventName: "AuthorizationRequest",
            referenceId: getSelfRef(order.id),
            details: [
                cardNumber: order.cardNumber,
                cardExpiration: order.expiration,
                amount: order.items.sum{it.price * it.quantity} 
            ]    
        )
        println "Authorization request sent (ref: ${getSelfRef(order.id)})"
	}

    def fulfill(order) {
        order.items.each { 
            eventService.publish(
                eventName: "FulfillmentRequest",
                referenceId: getSelfRef(order.id, it.id),
                details: [
                    sku: it.sku,
                    vendor: "AnyCorp",
                    customerName: order.customer.name,
                    customerAddress: order.customer.address
                ]
            )
            println "Fulfillment request sent (ref: ${getSelfRef(order.id)}, sku: ${it.sku})"
        }
	}

	def notifyOnShipped(order, refId) {
	    eventService.publish(
            eventName: "NotificationRequest", 
            referenceId: getSelfRef(order.id, refId),
            details: [
                email: order.customer.email,
                subject: "Your item has shipped!",
                body: "We thought you would like to know that your item just shipped."
            ]
        )
	}
    
    def cancel(order) {
        eventService.publish(eventName: "OrderCancelled", referenceId: getSelfRef(order.id))
    	println "Order (ref: ${getSelfRef(order.id)}) cancelled"
	}

    def handleEvent(ev) {
        switch (ev.eventName) {

            case "NewSimpleMerchOrderAsync":
                println "New order notification received (ref: ${getSelfRef(ev.referenceId)})"
                begin(orderService.findOrder(getSelfRef(ev.referenceId)))
                break

            case "ChargeAuthorized":
                println "Charge authorized (ref: ${getSelfRef(ev.referenceId)})"
                def order = orderService.findOrder(getSelfRef(ev.referenceId))
                if (order.items.sum{it.price * it.quantity} <= 50.00) {
                    fulfill(order)
                }
                break

            case "ChargeSettled":
                println "Charge settled (ref: ${getSelfRef(ev.referenceId)})"
                def order = orderService.findOrder(getSelfRef(ev.referenceId))
                if (order.items.sum{it.price * it.quantity} > 50.00) {
                    fulfill(order)
                }
                break

            case "ChargeDeclined":
                println "Charge declined (ref: ${getSelfRef(ev.referenceId)})"
                cancel(orderService.findOrder(getSelfRef(ev.referenceId)))
                break        

            case "FulfillmentAccepted":
                println "Fulfillment request accepted (ref: ${getSelfRef(ev.referenceId)})"
                break    

            case "ItemShipped":
                notifyOnShipped(orderService.findOrder(getSelfRef(ev.referenceId)), ev.referenceId)
                break
                
            case "UpdatedSimpleMerchOrderAsync":
                println "Updated order notification received (ref: ${getSelfRef(ev.referenceId)})"
                
                // In reality a order status check and/or nature of the modified 
                // data would likely be performed here to decide what processing 
                // step should be triggered.
                
                begin(orderService.findOrder(getSelfRef(ev.referenceId)))
                break

        }
        return null
    }

    def onMessage(msg) {
        def ev = JSON.parse(msg)
        handleEvent(ev)
    }
    
    String getSelfRef(refId, lineRefId = null) {
        def oId = Util.parseRefId(refId).order
        def selfId = "/order/${oId}/simple-merch-a/${oId}"
        if (lineRefId) {
            def lineId = Util.parseRefId(lineRefId)["line-item"]
            selfId += "/line-item/${lineId}"
        }
        return selfId
    }
}