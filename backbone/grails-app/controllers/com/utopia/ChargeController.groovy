package com.utopia

import org.springframework.dao.DataIntegrityViolationException

class ChargeController {

    static realtimeResponse = BillingStatus.Authorized

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def billingService

    def index() {
        switch (request.method) {
            case "GET":
                redirect(action: "list", params: params)
                break

            case "POST":
                def charge = new Charge(request.JSON) 
                charge.status = realtimeResponse
                billingService.process(charge)
                render (contentType: 'application/json', status: charge.status == BillingStatus.Error ? 500 : 200) {
                    if (charge.status != BillingStatus.Error) {
                        id = charge.selfRefId
                    }
                    status = charge.status.toString().toLowerCase()
                    details = charge.errors
                } 
                break
        }
    }

    def control() {
        if (params.id) {
            realtimeResponse = BillingStatus.valueOf(params.id)
            println "Billing service real-time response: ${realtimeResponse.name().toLowerCase()}"
        }
        [realtimeResponse: realtimeResponse]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [chargeInstanceList: Charge.list(params), chargeInstanceTotal: Charge.count()]
    }

    def create() {
        [chargeInstance: new Charge(params)]
    }

    def save() {
        def chargeInstance = new Charge(params)
        if (!chargeInstance.save(flush: true)) {
            render(view: "create", model: [chargeInstance: chargeInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'charge.label', default: 'Charge'), chargeInstance.id])
        redirect(action: "show", id: chargeInstance.id)
    }

    def show() {
        def chargeInstance = Charge.get(params.id)
        if (!chargeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'charge.label', default: 'Charge'), params.id])
            redirect(action: "list")
            return
        }

        [chargeInstance: chargeInstance]
    }

    def edit() {
        def chargeInstance = Charge.get(params.id)
        if (!chargeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'charge.label', default: 'Charge'), params.id])
            redirect(action: "list")
            return
        }

        [chargeInstance: chargeInstance]
    }

    def update() {
        def chargeInstance = Charge.get(params.id)
        if (!chargeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'charge.label', default: 'Charge'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (chargeInstance.version > version) {
                chargeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'charge.label', default: 'Charge')] as Object[],
                          "Another user has updated this Charge while you were editing")
                render(view: "edit", model: [chargeInstance: chargeInstance])
                return
            }
        }

        def origStatus = chargeInstance.status
        chargeInstance.properties = params
        
        if (!chargeInstance.save(flush: true)) {
            render(view: "edit", model: [chargeInstance: chargeInstance])
            return
        }

        billingService.publish(chargeInstance)

		flash.message = message(code: 'default.updated.message', args: [message(code: 'charge.label', default: 'Charge'), chargeInstance.id])
        redirect(action: "show", id: chargeInstance.id)
    }

    def delete() {
        def chargeInstance = Charge.get(params.id)
        if (!chargeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'charge.label', default: 'Charge'), params.id])
            redirect(action: "list")
            return
        }

        try {
            chargeInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'charge.label', default: 'Charge'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'charge.label', default: 'Charge'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
