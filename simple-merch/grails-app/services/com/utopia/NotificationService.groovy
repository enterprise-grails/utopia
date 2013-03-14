package com.utopia

import grails.web.JSONBuilder

class NotificationService {

    static transactional = false

    def grailsApplication

    def notify(refId, email, subject, content) {
	def resp =  withRest(uri: grailsApplication.config.endpoints.notification) {
	    post(
        	requestContentType: 'application/json',
        	body: new JSONBuilder().build() {
        	    delegate.referenceId = refId
        	    delegate.email = email
        	    delegate.subject = subject
        	    delegate.body = content
        	}.toString()
            )
        }
        return resp.data.id
    }
}
