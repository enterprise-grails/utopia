package com.utopia

import grails.converters.*
import groovy.json.JsonOutput

class MsgBusListenerService {

    static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true

    def onMessage(msg) {
        event topic:"msgEvent", data:msg
        
        return null
    }
}
