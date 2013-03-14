package com.utopia

import grails.converters.*
import grails.web.JSONBuilder

class OrderRouterService {
    
    static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true
    static selector = "eventName in ('NewOrder', 'OrderUpdated')"
    static container = "durable"
    
    def grailsApplication
    def eventService

    def onMessage(msg) {
        
        def json = JSON.parse(msg)
        println "Event received: ${json.eventName}"
        def cfg = grailsApplication.config.endpoints.order
        
        def order = withRest(uri: cfg.url) {
            auth.basic cfg.user, cfg.passwd
            get(requestContentType: "application/json", path: "${json.referenceId.split("/").last()}")
        }

        if (!order) {
            println "Order record not found, aborting processing (refId: ${json.referenceId})"
            return null
        }

        switch (RoutingController.currentRouting) {
            case RoutingType.Asynchronous:
                if (json.eventName == 'NewOrder') {
                    eventService.publish(
                        eventName: "NewSimpleMerchOrderAsync",
                        referenceId: order.data.id
                    )
                } else if (json.eventName == 'OrderUpdated') {
                    eventService.publish(
                        eventName: "UpdatedSimpleMerchOrderAsync",
                        referenceId: order.data.id
                    )
                }
                println "Order dispatched asynchronously"
                break

            case RoutingType.Synchronous:
                def resp = withRest(uri: grailsApplication.config.endpoints.simple.merch) {
                    post(requestContentType: "application/json", body: order.data)
                }
                println "Order dispatched synchronously (HTTP response: ${resp.status})"
                break    
        }
        

        /* Uncomment below to generate sysBus log activity and simulate some system-level blowup
         * to cause JMS transaction to rollback */
        // log.error "Fake error to test logging"
        // throw new RuntimeException("Some required resource is not available - explode!")
        
        return null
    }
}
