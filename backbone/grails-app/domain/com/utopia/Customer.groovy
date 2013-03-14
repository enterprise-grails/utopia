package com.utopia

class Customer {
    
    String firstName
    String lastName
    String street
    String city
    String state
    String zip
    String email
    
    Date dateCreated
    Date lastUpdated
    
    static hasMany = [orders:Order]

    static constraints = {
        firstName blank:false
        lastName blank:false
        street blank:false
        city blank:false
        state blank:false
        zip blank:false
        email email:true
    }
    
    String toString() {
        "${firstName} ${lastName}"
    }
}
