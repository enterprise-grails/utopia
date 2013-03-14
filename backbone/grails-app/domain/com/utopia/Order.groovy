package com.utopia

class Order {

    String cardNumber
    String expiration
    Date dateCreated
    Date lastUpdated
    
    static belongsTo = [customer:Customer]
    static hasMany = [lineItems:LineItem, lifeCycleEvents:LifeCycleEvent]

    static constraints = {
        cardNumber nullable:true
        expiration nullable:true
    }
    
    static mapping = {
        table "orders"
        lineItems sort:'id', order:'asc'
        lifeCycleEvents sort:'id', order:'asc'
    }
    
    String toString() {
        "${customer} ${dateCreated}"
    }
    
    String getSelfRefId() { "/order/${id}" }
}
