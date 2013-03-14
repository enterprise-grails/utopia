package com.utopia

import grails.converters.*
import grails.web.JSONBuilder

import org.springframework.context.*

class HttpListenerService implements ApplicationContextAware, ApplicationListener<RecordCapturedEvent> {

	static transactional = false

	ApplicationContext applicationContext

	def tape

	def onResponse(String requestLine, String responseStatusLine, String responsePayload) {
		def record = [
		    requestLine: requestLine,
		    responseStatusLine: responseStatusLine,
		    responsePayload: responsePayload
		]
		applicationContext.publishEvent(new RecordCapturedEvent(record)) 
	}

	void onApplicationEvent(RecordCapturedEvent event) {
		if (!tape) {
			return
		}
        def json = JSON.parse(event.source.responsePayload)
    	def builder = new JSONBuilder().build() {
            request = event.source.requestLine
            response = {
            	status = event.source.responseStatusLine
            	payload = json
            }
        }
        Record.withTransaction { status ->
            new Record(
          	    type: RecordType.Http, 
           	    tape: tape, 
           	    message: builder.toString()
            ).save(flush:true)
        }
	}
}