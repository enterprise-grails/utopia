package com.utopia.pages

import grails.test.mixin.*
import grails.test.mixin.support.*

import spock.lang.*
import geb.Page

class ShowOrderPage extends Page {
    static url = "odie/show"
    
    static at = { title == "Show Order" }
    
    static content = {
        events { moduleList EventModule, $("div.life-cycle-event") }
    }
    
}
