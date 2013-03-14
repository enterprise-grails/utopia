package com.utopia

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

@TestMixin(GrailsUnitTestMixin)
class UtilSpec extends spock.lang.Specification {
    
    def "parse a date from JSON"() {
        setup:
        def cal = new GregorianCalendar()
        cal.timeZone = TimeZone.getTimeZone("EST")
        cal.set Calendar.HOUR_OF_DAY, 12
        cal.set Calendar.MINUTE, 41
        cal.set Calendar.SECOND, 48
        cal.set Calendar.MILLISECOND, 0
        cal.set Calendar.DAY_OF_MONTH, 6
        cal.set Calendar.MONTH, 8
        cal.set Calendar.YEAR, 2012
        
        expect:
        Util.dateFromJSON("2012-09-06T17:41:48Z").time == cal.time.time
    }

    def "parse a null date"() {
        when:
        Util.dateFromJSON(null)
        
        then:
        thrown(NullPointerException)
    }

    def "parse an invalid date string"() {
        expect:
        Util.dateFromJSON("234234234234234") == null
    }

    def "get unique random strings"() {
        setup:
        def output = new HashSet()
        
        when:
        10000.times {
            output << Util.unique
        }
        
        then:
        output.size() == 10000
    }
    
    def "parse refID elements into a map"() {
        expect:
        Util.parseRefId(id) == map
        
        where:
        id                          | map
        null                        | [:]
        "order"                     | [order:null]
        "/order"                    | [order:null]
        "/order/"                   | [order:null]
        "/order/1"                  | [order:"1"]
        "/order/asdf"               | [order:"asdf"]
        "/order/asdf/blah"          | [order:"asdf", blah:null]
        "/order/asdf/foo/bar"       | [order:"asdf", foo:"bar"]
    }

}
