package com.utopia

import grails.converters.*
import groovy.json.JsonOutput

class SysBusListenerService {

    static exposes = ['jms']
    static destination = "sysBus"
    static isTopic = true
    static adapter = "noconversion"

    def onMessage(msg) {
        def level = msg.getStringProperty("level")
        def app = msg.getStringProperty("application")
        def text = msg.text
        def json = JSON.parse(text)
        log.debug "Sponge received: ${level} from ${app}"
        log.debug JsonOutput.prettyPrint(text)
        
        def event = new LogEvent(
                published: Date.parse("yyyy-MM-dd'T'HH:mm:ssz", json.published.replaceAll(/(\d)Z$/, { it[1] + "UTC" })),
                level: level,
                application: app,
                location: json.location,
                message: json.message,
                exception: json.exception?.type,
                trace: json.exception?.trace)
        event.save(failOnError: true)

        return null
    }
}
