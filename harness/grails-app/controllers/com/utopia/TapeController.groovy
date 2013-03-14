package com.utopia

class TapeController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tapeInstanceList: Tape.list(params), tapeInstanceTotal: Tape.count()]
    }

    def show() {
        def tapeInstance = Tape.get(params.id)
        if (!tapeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'tape.label', default: 'Tape'), params.id])
            redirect(action: "list")
            return
        }
        [tapeInstance: tapeInstance]
    }
}
