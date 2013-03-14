package com.utopia

import grails.web.JSONBuilder

class EventService {
    
    def jmsService
    def grailsApplication

    def publish(Map args) {
        def builder = new JSONBuilder().build() {
            eventName = args.eventName
            referenceId = args.referenceId
        	published = new Date()
            if (args.details) details = args.details
        }
        
        def cfg = grailsApplication.config.eventDriven
        
        jmsService.send(topic:cfg.bus.topic ?: "msgBus", builder.toString(), cfg.bus.template ?: "standard") { msg ->
            msg.setStringProperty "eventName", args.eventName
            msg.setStringProperty "referenceId", args.referenceId
        }
    }
}
