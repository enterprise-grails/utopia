package com.utopia.pages

import grails.test.mixin.*
import grails.test.mixin.support.*

import spock.lang.*
import geb.Page

class EditFulfillmentPage extends Page {
    static url = "fulfillment/edit"
    
    static at = { title == "Edit Fulfillment" }
    
    static content = {
        ffStatus { $("select", name: "status") }
        update { $("input", type: "submit") }
    }
}
