package com.utopia

class Fulfillment {

    String referenceId
	String sku
	String vendor
    String name
    String address
	FulfillmentStatus status

    Date dateCreated
    Date lastUpdated

    static constraints = {
        referenceId blank: false
    	sku blank: false
    	vendor blank: false
        name blank: false
        address blank: false
    	status nullable: false
    }
    
    String getSelfRefId() { "${referenceId}/fulfillment/${id}" }
}
