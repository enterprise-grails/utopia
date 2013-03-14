package com.utopia

class LifeCycleEvent {
    
    String eventName
    String referenceId
    Date published
    Date dateCreated
    Date lastUpdated
    String details
    
    static belongsTo = [order:Order]

    static constraints = {
        referenceId blank:false
        details nullable:true
    }
    
    static mapping = {
        details type:"text"
    }
    
    String toString() {
        "${eventName}"
    }
}
