package com.utopia

import grails.converters.*

class RequestListenerService {

    static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true
    static container = "reqs"
    static selector = "eventName IN ('AuthorizationRequest', 'FulfillmentRequest', 'NotificationRequest')"

    def billingService
    def fulfillmentService
    def notificationService

    def onMessage(msg) {
        def ev = JSON.parse(msg)
        println "RequestListener received: ${ev.eventName}"
        
        switch (ev.eventName) {

            case "AuthorizationRequest":
                def charge = new Charge(
                    referenceId: ev.referenceId,
                    cardNumber: ev.details.cardNumber,
                    expiration: ev.details.cardExpiration,
                    amount: ev.details.amount
                )
                charge.status = ChargeController.realtimeResponse
                billingService.process(charge)
                break

            case "FulfillmentRequest":
                def ff = new Fulfillment(
                    referenceId: ev.referenceId,
                    sku: ev.details.sku,
                    vendor: ev.details.vendor,
                    name: ev.details.customerName,
                    address: ev.details.customerAddress
                )
                ff.status = FulfillmentController.realtimeResponse
                fulfillmentService.process(ff)
                break
                
            case "NotificationRequest":
                notificationService.process(
                    referenceId: ev.referenceId,
                    email: ev.details.email,
                    subject: ev.details.subject,
                    body: ev.details.body
                )
                break
        }

        /* Uncomment below to simulate some system-level blowup to cause JMS and DB transaction to rollback */
        // println "Some required resource is not available - explode!"
        // throw new RuntimeException("Some required resource is not available - explode!")
        
        return null
    }
}
