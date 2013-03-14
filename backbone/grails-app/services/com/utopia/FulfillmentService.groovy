package com.utopia

class FulfillmentService {
    
    static transactional = false

    def eventService

    def process(ff) {
        if (!ff.save(flush: true)) {
            ff.status = FulfillmentStatus.Error
        } 
        publish(ff)
    }

    def publish(ff) {
        def evt, msg
        switch (ff.status) {
            case FulfillmentStatus.Accepted: 
                evt = 'FulfillmentAccepted'
                break
            case FulfillmentStatus.Shipped: 
                evt = 'ItemShipped'
                break
            case FulfillmentStatus.Error: 
                evt = 'FulfillmentException'
                break
        }    
        if (evt) {
            eventService.publish(eventName: evt, referenceId: "${ff.referenceId}/fulfillment/${ff.id}")
        } 
    }
}
