package com.utopia

import grails.converters.*
import grails.plugins.springsecurity.Secured

class OcsController {
    
    static serviceResponse = ServiceResponse.Ok

    def eventService

    def control() {
        if (params.id) {
            serviceResponse = ServiceResponse.valueOf(params.id)
            println "Order service response: ${serviceResponse.name().toLowerCase()}"
        }
        [serviceResponse: serviceResponse]
    }

    @Secured(['ROLE_API_USER'])
    def save() {
        
        def json = request.JSON
        def jcust = json.customer
        def valErrors = []

        def cust = Customer.findAll {
            firstName == jcust.firstName
            lastName == jcust.lastName
            email == jcust.email
            zip == jcust.zip
        }

        if (cust?.size() == 1) {
            cust = cust[0]
        } else {
            cust = new Customer(firstName: jcust.firstName, lastName: jcust.lastName, street: jcust.street, city: jcust.city, state: jcust.state, zip: jcust.zip, email: jcust.email)
            if (!cust.validate()) valErrors << cust.errors
        }

        def order = new Order(customer:cust, cardNumber:json.cardNumber, expiration:json.expiration)
        if (!order.validate()) valErrors << order.errors

        json.lineItems?.each { jitem ->
            def prod = Product.get(jitem.product?.id)
            def line = new LineItem(order:order, product:prod, quantity:jitem.quantity, price:jitem.price)
            if (line.validate()) {
                order.addToLineItems(line)
            } else {
                valErrors << line.errors
            }
        }

        if (!valErrors) {
            Order.withTransaction { status ->
                cust.save(failOnError:true)
                order.save(failOnError:true)
            }
            
            eventService.publish(eventName: "NewOrder", referenceId: order.selfRefId)
            
            render(contentType: "application/json") {
                id = order.selfRefId
            }
            
        } else {
            render(contentType: "application/json", status: 500) {
                details = valErrors
            }
        }
    }
    
    @Secured(['ROLE_API_USER'])
    def show() {
        switch (serviceResponse) {

            case ServiceResponse.Ok:
                def order = Order.get(params.id)
                if (order) {
                    render(contentType: "application/json") {
                        id = order.selfRefId
                        customer {
                            name = "${order.customer.firstName} ${order.customer.lastName}"
                            address = "${order.customer.street}, ${order.customer.city}, ${order.customer.state}, ${order.customer.zip}"
                            email = order.customer.email
                        }
                        if (order.cardNumber) cardNumber = order.cardNumber
                        if (order.expiration) expiration = order.expiration
                        items = array {
                            order.lineItems.each { i->
                                item id:"${order.selfRefId}/line-item/${i.id}", sku: i.product.sku, price: i.price, quantity: i.quantity
                            }
                        }
                    }
                } else {
                    render(contentType: "application/json", status: 404) {
                        details = "Order with id: ${params.id} not found"
                    }
                }
                break

            case ServiceResponse.NotFound:
                render(contentType: "application/json", status: 404) {
                    details = "Simulated HTTP 404 Not Found"
                }
                break

            case ServiceResponse.Error:
                render(contentType: "application/json", status: 500) {
                    details = "Simulated HTTP 500 Internal Server Error"
                }
                break
        }
    }
}
