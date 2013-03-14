package com.utopia

import grails.converters.*

class ServiceConsoleController {
    
    def jmsService
    def grailsApplication

    def index() { }
    
    def invoke() {
        def resp
        def endpoints = [
            OCS: "${createLink controller:"ocs", absolute:true}/",
            Billing: "${createLink controller:"charge", absolute:true}/",
            Fulfillment: "${createLink controller:"fulfillment", absolute:true}/",
            Notification: "${createLink controller:"notification", absolute:true}/"
        ]
        int count = params.int('count') ?: 1
        int delay = params.int('delay') ?: 0
        
        count.times {
            if (params.service == "Event") {
                def json = JSON.parse(params.payload)
            
                jmsService.send(topic:"msgBus", params.payload, "standard") { msg ->
                    msg.setStringProperty "eventName", json.eventName
                    msg.setStringProperty "referenceId", json.referenceId
                }
            
                render view:'index', model:[resp:[status: 200]]
            } else {
                try {
                    resp = withRest(uri:endpoints[params.service]) {
                        if (params.service == "OCS") {
                            def cfg = grailsApplication.config.endpoints.order
                            auth.basic cfg.user, cfg.passwd
                        }
                        post(body:params.payload, requestContentType:"application/json")
                    }
                } catch (Exception ex) {
                    resp = ex.response
                }
            
                render view:'index', model:[resp:resp]
            }
            
            if (params.delay) Thread.sleep(delay)
        }
    }
}
