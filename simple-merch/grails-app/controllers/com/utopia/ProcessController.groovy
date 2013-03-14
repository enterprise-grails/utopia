package com.utopia

class ProcessController {

	def syncProcessService

    def index() {
    	def status
    	try {
            syncProcessService.begin(request.JSON)
            status = 200
    	} catch (Exception ex) {
    		log.error ex
            status = 500
    	}
        render (contentType: 'application/json', status: status, text: "{}")
    }
}
