package com.utopia

import org.springframework.dao.DataIntegrityViolationException

class FulfillmentController {

    static realtimeResponse = FulfillmentStatus.Accepted

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    
    def fulfillmentService
    
    def index() {
        switch (request.method) {
            case "GET":
                redirect(action: "list", params: params)
                break

            case "POST":            
                def ff = new Fulfillment(request.JSON)
                ff.status = realtimeResponse
                if (!ff.save(flush: true)) {
                    ff.status = FulfillmentStatus.Error
                } 

                fulfillmentService.process(ff)

                render (contentType: 'application/json', 
                        status: (ff.status == FulfillmentStatus.Error ? 500 : 200)) {
                    if (ff.status != FulfillmentStatus.Error) {
                        id = ff.selfRefId
                    }
                    status = ff.status.name().toLowerCase()
                    details = ff.errors
                }
                break
        }
    }

    def control() {
        if (params.id) {
            realtimeResponse = FulfillmentStatus.valueOf(params.id)
            println "Fulfillment service real-time response: ${realtimeResponse.name().toLowerCase()}"
        }
        [realtimeResponse: realtimeResponse]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [fulfillmentInstanceList: Fulfillment.list(params), fulfillmentInstanceTotal: Fulfillment.count()]
    }

    def create() {
        [fulfillmentInstance: new Fulfillment(params)]
    }

    def save() {
        def fulfillmentInstance = new Fulfillment(params)
        if (!fulfillmentInstance.save(flush: true)) {
            render(view: "create", model: [fulfillmentInstance: fulfillmentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), fulfillmentInstance.id])
        redirect(action: "show", id: fulfillmentInstance.id)
    }

    def show() {
        def fulfillmentInstance = Fulfillment.get(params.id)
        if (!fulfillmentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), params.id])
            redirect(action: "list")
            return
        }

        [fulfillmentInstance: fulfillmentInstance]
    }

    def edit() {
        def fulfillmentInstance = Fulfillment.get(params.id)
        if (!fulfillmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), params.id])
            redirect(action: "list")
            return
        }

        [fulfillmentInstance: fulfillmentInstance]
    }

    def update() {
        def fulfillmentInstance = Fulfillment.get(params.id)
        if (!fulfillmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (fulfillmentInstance.version > version) {
                fulfillmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'fulfillment.label', default: 'Fulfillment')] as Object[],
                          "Another user has updated this Fulfillment while you were editing")
                render(view: "edit", model: [fulfillmentInstance: fulfillmentInstance])
                return
            }
        }

        def origStatus = fulfillmentInstance.status
        fulfillmentInstance.properties = params

        if (!fulfillmentInstance.save(flush: true)) {
            render(view: "edit", model: [fulfillmentInstance: fulfillmentInstance])
            return
        }
        
        fulfillmentService.publish(fulfillmentInstance)
 
		flash.message = message(code: 'default.updated.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), fulfillmentInstance.id])
        redirect(action: "show", id: fulfillmentInstance.id)
    }

    def delete() {
        def fulfillmentInstance = Fulfillment.get(params.id)
        if (!fulfillmentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            fulfillmentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'fulfillment.label', default: 'Fulfillment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
