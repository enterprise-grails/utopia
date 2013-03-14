package com.utopia

import groovy.json.JsonOutput

class RefController {
    
    def grailsApplication

    def show() {
        def map = Util.parseRefId(params.path)
        def lastKey =  map.collect { k,v -> k }.last()

        def resp
        try {
            if (lastKey in ["order", "simple-merch", "simple-merch-a"]) {
                def cfg = grailsApplication.config.endpoints.order
                resp = withRest(uri: cfg.url) {
                    auth.basic cfg.user, cfg.passwd
                    get(requestContentType: "application/json", path: "${map[lastKey]}")
                }
            } else {
                render(contentType:"application/json", status:404) {
                    details = "Could not resolve ${params.path}"
                }
                return
            }
        } catch (Exception ex) {
            log.error "Service call failed", ex
            render(contentType:"application/json", status:500) {
                details = "Encountered error in delegate service"
            }
            return
        }
        
        render(contentType:"application/json", status:resp.status, text:JsonOutput.prettyPrint(resp.data.toString()))
    }
}
