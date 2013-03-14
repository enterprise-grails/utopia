package com.utopia

import grails.web.JSONBuilder

class FulfillmentService {
	
    static transactional = false

    def grailsApplication

    def fulfill(refId, sku, vendor, custName, custAddress) {
	def resp =  withRest(uri: grailsApplication.config.endpoints.fulfillment) {
	    post(
        	requestContentType: 'application/json',
        	body: new JSONBuilder().build() {
        	    delegate.referenceId = refId
        	    delegate.sku = sku
        	    delegate.vendor = vendor
        	    delegate.name = custName
        	    delegate.address = custAddress
        	}.toString()
            )
        }
        return resp.data.status.toLowerCase()
    }
}