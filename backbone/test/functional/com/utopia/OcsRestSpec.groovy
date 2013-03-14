package com.utopia

import static groovyx.net.http.ContentType.TEXT

import spock.lang.Specification
import spock.lang.Shared
import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseException

class OcsRestSpec extends Specification {
    
    def "try to access without credentials"() {
        when:
        ocs.auth.basic "foo", "bar"
        response = ocs.get(path:"${order2.id}", contentType:TEXT)
        
        then:
        def ex = thrown(HttpResponseException)
        ex.response.status == 401
        ex.response.contentType == "text/html"
    }
            
    def "get an order by id returns an order"() {
        when:
        response = ocs.get(path:"${order2.id}", contentType:TEXT)
        
        then:
        response.status == 200
        response.contentType == "application/json"
        response.data.text == """{
            |  "id": "/order/${order2.id}",
            |  "customer": {
            |    "name": "Jane Doe",
            |    "address": "123 Any Street Rd., Anytown, CT, 12345",
            |    "email": "me@here.com"
            |  },
            |  "cardNumber": "4111111111111111",
            |  "expiration": "06/15",
            |  "items": 
            |  [
            |    {
            |      "id": "/order/${order2.id}/line-item/${line1.id}",
            |      "sku": "sku123",
            |      "price": 23.45,
            |      "quantity": 1
            |    },
            |    {
            |      "id": "/order/${order2.id}/line-item/${line2.id}",
            |      "sku": "sku456",
            |      "price": 43.95,
            |      "quantity": 2
            |    }
            |  ]
            |}""".stripMargin()
    }
    
    def "get a minimal order by id returns only fields with values"() {
        when:
        response = ocs.get(path:"${order1.id}", contentType:TEXT)
        
        then:
        response.status == 200
        response.contentType == "application/json"
        response.data.text == """{
            |  "id": "/order/${order1.id}",
            |  "customer": {
            |    "name": "Joe Smith",
            |    "address": "123 Any Street Rd., Anytown, CT, 12345",
            |    "email": "me@here.com"
            |  },
            |  "items": 
            |  [
            |  ]
            |}""".stripMargin()
    }
    
    def "get with an invalid order id returns a 404"() {
        when:
        ocs.get(path:"99999", contentType:TEXT)
        
        then:
        def ex = thrown(HttpResponseException)
        ex.response.status == 404
        ex.response.contentType == "application/json"
        ex.response.data.text == """{
            |  "details": "Order with id: 99999 not found"
            |}""".stripMargin()
    }
    
    def ocs, response, order1, order2, line1, line2

    def setup() {
        ocs = new RESTClient("http://localhost:8080/backbone/ocs/")
        ocs.auth.basic "backbone", "asdf"
        
        /* Minimal order */
        def cust1 = new Customer(firstName:"Joe", lastName:"Smith", street:"123 Any Street Rd.", city:"Anytown", state:"CT", zip:"12345", email:"me@here.com").save(failOnError:true)
        order1 = new Order(customer:cust1).save(failOnError:true)
        
        /* Full order */
        def cust2 = new Customer(firstName:"Jane", lastName:"Doe", street:"123 Any Street Rd.", city:"Anytown", state:"CT", zip:"12345", email:"me@here.com").save(failOnError:true)
        order2 = new Order(customer:cust2, cardNumber:"4111111111111111", expiration:"06/15").save(failOnError:true)
        def prod1 = new Product(sku: "sku123", name: "Widget", description: "The classic widget", price: 23.45).save(failOnError:true)
        def prod2 = new Product(sku: "sku456", name: "Super Widget", description: "The new and improved widget", price: 43.95).save(failOnError:true)
        line1 = new LineItem(order: order2, product: prod1, price: 23.45, quantity: 1).save(failOnError:true)
        line2 = new LineItem(order: order2, product: prod2, price: 43.95, quantity: 2).save(failOnError:true)
        
        assert Customer.count() == 2
        assert Order.count() == 2
        assert Product.count() == 2
        assert LineItem.count() == 2
    }
    
    def cleanup() {
        LineItem.executeUpdate("delete from LineItem")
        Order.executeUpdate("delete from Order")
        Customer.executeUpdate("delete from Customer")
        Product.executeUpdate("delete from Product")
        
        assert Customer.count() == 0
        assert Order.count() == 0
        assert Product.count() == 0
        assert LineItem.count() == 0
    }
}
