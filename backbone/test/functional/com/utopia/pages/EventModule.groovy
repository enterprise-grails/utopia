package com.utopia.pages

import grails.test.mixin.*
import grails.test.mixin.support.*

import spock.lang.*
import geb.Module

class EventModule extends Module {
    static content = {
        name { $("p a").text() }
        refId {
            def url = $("a.ref-id").@href
            url[url.lastIndexOf("/") + 1..url.length() - 1]
        }
    }
}
