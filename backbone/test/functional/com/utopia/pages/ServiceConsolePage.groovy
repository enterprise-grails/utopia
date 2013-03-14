package com.utopia.pages

import grails.test.mixin.*
import grails.test.mixin.support.*

import spock.lang.*
import geb.Page

class ServiceConsolePage extends Page {
    static url = "serviceConsole"
    
    static at = { title == "Web Service Test Console" }
    
    static content = {
        post { $("input", name: "post") }
        result { $("div.status").text() }
        orderId { $("div#response-data").text().find(/\d/) }
    }
    
}
