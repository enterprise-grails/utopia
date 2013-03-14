package com.utopia.pages

import grails.test.mixin.*
import grails.test.mixin.support.*

import spock.lang.*
import geb.Page

class ShowFulfillmentPage extends Page {
    static url = "fulfillment/show"
    
    static at = { title == "Show Fulfillment" }
    
    static content = {
        message { $("div.message").text() }
    }
}
