package com.utopia

import com.utopia.pages.*
import grails.test.mixin.*
import grails.test.mixin.support.*

import spock.lang.*
import geb.spock.GebReportingSpec
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes as GA

@Stepwise
class HappyPathSpec extends GebReportingSpec {

    def "post an order to OCS"() {
        given:
        to ServiceConsolePage
        
        when:
        post.click()
        oId = orderId
        
        then:
        at ServiceConsolePage
        result == "200"
    }

    def "order should have 8 lifecycle events"() {
        given:
        Thread.sleep(5000)
        to ShowOrderPage, oId
        ff1Id = events[6]?.refId
        ff2Id = events[7]?.refId
        
        expect:
        events?.size() == 8
        events[0]?.name == "NewOrder"
        events[1]?.name == "NewSimpleMerchOrderAsync"
        events[2]?.name == "AuthorizationRequest"
        events[3]?.name == "ChargeAuthorized"
        events[4]?.name == "FulfillmentRequest"
        events[5]?.name == "FulfillmentRequest"
        events[6]?.name == "FulfillmentAccepted"
        events[7]?.name == "FulfillmentAccepted"
    }

    def "update the status of fulfillment item 1 to shipped"() {
        given:
        to EditFulfillmentPage, ff1Id
        
        when:
        ffStatus = "Shipped"
        update.click()
        
        then:
        at ShowFulfillmentPage
        message == "Fulfillment ${ff1Id} updated"
    }
    
    def "update the status of fulfillment item 2 to shipped"() {
        given:
        to EditFulfillmentPage, ff2Id
        
        when:
        ffStatus = "Shipped"
        update.click()
        
        then:
        at ShowFulfillmentPage
        message == "Fulfillment ${ff2Id} updated"
    }
    
    def "order should have 14 lifecycle events"() {
        given:
        Thread.sleep(5000)
        to ShowOrderPage, oId
        
        expect:
        events?.size() == 14
        events[0]?.name == "NewOrder"
        events[1]?.name == "NewSimpleMerchOrderAsync"
        events[2]?.name == "AuthorizationRequest"
        events[3]?.name == "ChargeAuthorized"
        events[4]?.name == "FulfillmentRequest"
        events[5]?.name == "FulfillmentRequest"
        events[6]?.name == "FulfillmentAccepted"
        events[7]?.name == "FulfillmentAccepted"
        events[8]?.name == "ItemShipped"
        events[9]?.name == "NotificationRequest"
        events[10]?.name == "NotificationSent"
        events[11]?.name == "ItemShipped"
        events[12]?.name == "NotificationRequest"
        events[13]?.name == "NotificationSent"
    }
    
    @Shared
    def oId, ff1Id, ff2Id
    
    def setupSpec() {
        new Product(sku: "sku123", name: "Widget", description: "The classic widget", price: 23.45).save(failOnError:true)
        new Product(sku: "sku456", name: "Super Widget", description: "The new and improved widget", price: 43.95).save(failOnError:true)
        assert Product.count() == 2
    }

    def cleanupSpec() {        
        LineItem.executeUpdate("delete from LineItem")
        LifeCycleEvent.executeUpdate("delete from LifeCycleEvent")
        Order.executeUpdate("delete from Order")
        Customer.executeUpdate("delete from Customer")
        Product.executeUpdate("delete from Product")
        
        assert LineItem.count() == 0
        assert LifeCycleEvent.count() == 0
        assert Order.count() == 0
        assert Customer.count() == 0
        assert Product.count() == 0
    }
}
