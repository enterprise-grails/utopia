package com.utopia

import grails.converters.*
import grails.web.JSONBuilder

class JmsListenerService {

	static transactional = false

	static exposes = ['jms']
    static destination = "msgBus"
    static isTopic = true
    static adapter = "noconversion"

    def tape

    def onMessage(msg) {
    	if (!tape) {
            return null
        }
    	
        def json = JSON.parse(msg.text)
    	def builder = new JSONBuilder().build() {
            headers = msg.properties
            event = json
        }
        new Record(
          	type: RecordType.Jms, 
           	tape: tape, 
           	message: builder.toString()
        ).save(flush:true)
        return null    
    } 
}