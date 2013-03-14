package com.utopia

import grails.util.GrailsNameUtils
import grails.web.JSONBuilder

class EventService {
    
    def jmsService

    def publish(Map args) {
        def builder = new JSONBuilder().build() {
            eventName = args.eventName
            referenceId = args.referenceId
        	published = new Date()
            if (args.details) details = args.details
        }
        
        jmsService.send(topic:"msgBus", builder.toString(), "standard") { msg ->
            msg.setStringProperty "eventName", args.eventName
            msg.setStringProperty "referenceId", args.referenceId
        }
    }
}
