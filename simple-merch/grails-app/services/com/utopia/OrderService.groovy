package com.utopia

class OrderService {
	
    static transactional = false

    def grailsApplication

    def findOrder(refId) {
        def cfg = grailsApplication.config.endpoints.order
        
        def resp = withRest(uri: cfg.url) {
            auth.basic cfg.user, cfg.passwd
            get(requestContentType: "application/json", path:"${refId.split("/").last()}")
        }
        if (resp.status != 200) {
            throw new RuntimeException("Error getting order data")
        }
        return resp.data
    }
}