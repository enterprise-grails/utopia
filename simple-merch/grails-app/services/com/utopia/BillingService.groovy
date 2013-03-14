package com.utopia

import grails.web.JSONBuilder

class BillingService {
	
    static transactional = false

    def grailsApplication

    def authorize(refId, cardNumber, cardExpiration, amount) {
        def resp = withRest(uri: grailsApplication.config.endpoints.billing) {
            post(
        	requestContentType: 'application/json',
        	body: new JSONBuilder().build() {
                    delegate.referenceId = refId
                    delegate.cardNumber = cardNumber
                    delegate.expiration = cardExpiration
                    delegate.amount = amount
                }.toString()
            )
        }
        return resp.data.status.toLowerCase()
    }
}