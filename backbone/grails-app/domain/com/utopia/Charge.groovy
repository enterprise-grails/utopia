package com.utopia

class Charge {

    String referenceId
	String cardNumber
	String expiration
	BigDecimal amount
	BillingStatus status

    static constraints = {
        referenceId blank: false
    	cardNumber blank: false, creditCard: true
    	expiration blank: false
    	amount min: 0.01
    	status nullable: false
    }
    
    String getSelfRefId() { "${referenceId}/charge/${id}" }
}
