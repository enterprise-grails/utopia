package com.utopia

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import co.freeside.betamax.Betamax
import co.freeside.betamax.Recorder
import grails.plugin.jms.JmsService
import grails.plugin.spock.IntegrationSpec

class RouterServiceSpec extends IntegrationSpec {
    
    // TODO: Betamax is failing because of basic authentication. Per docs this should work. Need to inevestigate.
    // @Betamax(tape="OCS_tape")
    def "a new order message is routed as a NewSimpleMerchOrderAsync message"() {
        when:
        orderRouterService.onMessage("""{
            |  "eventName": "NewOrder",
            |  "referenceId": "/order/1",
            |  "published": "2012-09-11T15:42:05Z"
            |}""".stripMargin())
        
        then:
        1 * jms.send(_, { it ==~ /\{"eventName":"NewSimpleMerchOrderAsync","referenceId":"\/order\/1","published":".+T.+Z"\}/ }, _, _)
    }

    //@Rule Recorder recorder = new Recorder(tapeRoot: new File("test/resources/tapes"))
    def eventService
    def orderRouterService
    def jms = Mock(JmsService)
    
    def setup() {
        eventService.jmsService = jms
        orderRouterService.eventService = eventService
    }

}
