package com.utopia

import java.text.*
import org.apache.commons.codec.digest.DigestUtils

class Util {
    static Date dateFromJSON(String str) {
        def f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        f.setTimeZone(TimeZone.getTimeZone("GMT"))
        return f.parse(str, new ParsePosition(0))
    }
    
    static String getUnique() {
    	DigestUtils.md5Hex(UUID.randomUUID().toString())
    }
    
    static Map parseRefId(str) {
        def tokens = (str.split('/') as List) - [null, ""]
        def map = [:]
        for (Iterator iter = tokens.iterator(); iter.hasNext(); ) {
            map << [(iter.next()):iter.hasNext() ? iter.next() : null]
        }
        return map
    }
}
