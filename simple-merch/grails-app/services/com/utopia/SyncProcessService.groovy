package com.utopia

import grails.converters.JSON

class SyncProcessService {

	static transactional = false

    static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true
    static container = "sync"
    static selector = "referenceId LIKE '/order/%/simple-merch/%'"

	def grailsApplication
	def billingService
    def eventService
    def fulfillmentService
    def notificationService
    def orderService

	def begin(order) {
		bill(order)
	}
    
	def bill(order) {
        def total = order.items.sum{it.price * it.quantity}
  	    def res = billingService.authorize(getSelfRef(order.id), order.cardNumber, order.expiration, total)
        println "Authorization result (ref: ${getSelfRef(order.id)}): ${res}"
        switch (res) {
        	case ['authorized']:
                if (total <= 50.00) {
                    fulfill(order)
                }
        	    break
            case ['settled']:
                fulfill(order)
                break
        	case 'declined':
        	    cancel(order)
        	    break
        }
	}

    def fulfill(order) {
        order.items.each { 
            def res = fulfillmentService.fulfill(getSelfRef(order.id, it.id), it.sku, "AnyCorp", order.customer.name, order.customer.address)
        	println "Fulfillment request result (ref: ${getSelfRef(order.id)}, sku: ${it.sku}): ${res}"
        }
	}

    def notifyOnShipped(order, refId) {
        def refMap = Util.parseRefId(refId)
        def lineItem = order.items.find { it.id =~ "/line-item/${refMap['line-item']}" }
        notificationService.notify(getSelfRef(order.id, lineItem.id), order.customer.email, "Your item has shipped!", "We thought you'd like to know that your item has shipped.")
	}

    def cancel(order) {
        eventService.publish(eventName: "OrderCancelled", referenceId: getSelfRef(order.id))
    	println "Order (ref: ${getSelfRef(order.id)}) cancelled"
	}

    def onMessage(msg) {
        def ev = JSON.parse(msg)
        switch (ev.eventName) {

            case "ChargeSettled":
                def order = orderService.findOrder(getSelfRef(ev.referenceId))
                if (order.items.sum{it.price * it.quantity} > 50.00) {
                    fulfill(order)
                }
                break

            case "ChargeDeclined":
                cancel(orderService.findOrder(getSelfRef(ev.referenceId)))
                break        

            case "ItemShipped":
                notifyOnShipped(orderService.findOrder(getSelfRef(ev.referenceId)), ev.referenceId)
                break

        }
        return null
    }
    
    String getSelfRef(refId, lineRefId = null) {
        def oId = Util.parseRefId(refId).order
        def selfId = "/order/${oId}/simple-merch/${oId}"
        if (lineRefId) {
            def lineId = Util.parseRefId(lineRefId)["line-item"]
            selfId += "/line-item/${lineId}"
        }
        
        return selfId
    }
}