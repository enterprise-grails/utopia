package com.utopia

import grails.plugin.spock.IntegrationSpec

class SysBusListenerSpec extends IntegrationSpec {
    
    def "log a new SysBus log message"() {
        given:
        def body = """{
        |    "published": "2012-10-08T17:40:38Z",
        |    "level": "WARN",
        |    "application": "backbone",
        |    "location": "grails.app.services.com.utopia.LifeCycleEventListenerService",
        |    "message": "Something may blow up",
        |}
        """.stripMargin()
        
        when:
        sysBusListenerService.onMessage(msg)
        def event = LogEvent.findByLocation("grails.app.services.com.utopia.LifeCycleEventListenerService")
        
        then:
        1 * msg.getStringProperty("level") >> "WARN"
        1 * msg.getStringProperty("application") >> "backbone"
        1 * msg.getText() >> body
        LogEvent.count() == 1
        event != null
        event.published == Date.parse("yyyy-MM-dd'T'HH:mm:ssz", "2012-10-08T17:40:38UTC")
        event.level == "WARN"
        event.application == "backbone"
        event.message == "Something may blow up"
        event.exception == null
        event.trace == null
        event.dateCreated != null
    }
    
    def "log a new SysBus log message with exception"() {
        given:
        def body = """{
        |    "published": "2012-10-08T17:44:26Z",
        |    "level": "ERROR",
        |    "application": "router",
        |    "location": "grails.app.services.com.utopia.OrderRouterService",
        |    "exception": {
        |        "type": "org.grails.blah@fj83jf8",
        |        "trace": "a stack trace goes here"
        |    }
        |}
        """.stripMargin()
        
        when:
        sysBusListenerService.onMessage(msg)
        def event = LogEvent.findByLocation("grails.app.services.com.utopia.OrderRouterService")
        
        then:
        1 * msg.getStringProperty("level") >> "ERROR"
        1 * msg.getStringProperty("application") >> "router"
        1 * msg.getText() >> body
        LogEvent.count() == 1
        event != null
        event.published == Date.parse("yyyy-MM-dd'T'HH:mm:ssz", "2012-10-08T17:44:26UTC")
        event.level == "ERROR"
        event.application == "router"
        event.message == null
        event.exception == "org.grails.blah@fj83jf8"
        event.trace == "a stack trace goes here"
        event.dateCreated != null
    }
    
    def sysBusListenerService
    def msg = Mock(javax.jms.TextMessage)
}
