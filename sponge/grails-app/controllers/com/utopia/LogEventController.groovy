package com.utopia

import org.springframework.dao.DataIntegrityViolationException

class LogEventController {
    
    static defaultAction = "list"

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [logEventInstanceList: LogEvent.list(params), logEventInstanceTotal: LogEvent.count()]
    }

    def show() {
        def logEventInstance = LogEvent.get(params.id)
        if (!logEventInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'logEvent.label', default: 'LogEvent'), params.id])
            redirect(action: "list")
            return
        }

        [logEventInstance: logEventInstance]
    }
}
