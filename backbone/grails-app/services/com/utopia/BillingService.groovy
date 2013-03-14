package com.utopia

class BillingService {
    
    static transactional = false

    def eventService

    def process(charge) {
        if (!charge.save(flush: true)) {
            charge.status = BillingStatus.Error
        } 
        publish(charge)
    }

    def publish(charge) {
        def evt, msg
        switch (charge.status) {
            case BillingStatus.Authorized: 
                evt = 'ChargeAuthorized'
                break
            case BillingStatus.Settled: 
                evt = 'ChargeSettled'
                break
            case BillingStatus.Declined: 
                evt = 'ChargeDeclined'
                break
            case BillingStatus.Error: 
                evt = 'ChargeException'
                break
        }    

        if (evt) {
            eventService.publish(eventName: evt, referenceId: "${charge.referenceId}/charge/${charge.id}")
        } 
    }
}
