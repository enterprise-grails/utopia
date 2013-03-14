package com.utopia

import grails.converters.*
import groovy.json.JsonOutput

class LifeCycleListenerService {

    static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true
    static adapter = "noconversion"
    static container = "lifecycle"

    def onMessage(msg) {
        def eventName = msg.getStringProperty('eventName')
        def refId = msg.getStringProperty("referenceId")
        println "LifeCycleListenerService received: ${eventName}"
        println "ReferenceId: ${refId}"
        println JsonOutput.prettyPrint(msg.text)

        def oId = Util.parseRefId(refId).order as Long
        def order = Order.get(oId)
        if (!order) throw new RuntimeException("Order for id: ${oId} not found")
        
        def json = JSON.parse(msg.text)
        new LifeCycleEvent(
            order: order, 
            eventName: eventName, 
            referenceId: refId, 
            published: Util.dateFromJSON(json.published),
            details:json.details?.toString()
        ).save(failOnError:true)
        return null
    }
}
