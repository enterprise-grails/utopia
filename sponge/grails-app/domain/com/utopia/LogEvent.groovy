package com.utopia

class LogEvent {
    
    Date published
    String level
    String application
    String location
    String message
    String exception
    String trace
    Date dateCreated

    static constraints = {
        level blank:false
        application blank:false
        location nullable:true
        message nullable:true
        exception nullable:true
        trace nullable:true
    }
    
    static mapping = {
        level index:"level_idx"
        application index:"application_idx"
        location index:"location_idx"
        message type:"text"
        trace type:"text"
    }
}
