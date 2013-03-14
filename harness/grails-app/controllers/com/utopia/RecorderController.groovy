package com.utopia

class RecorderController {

    def jmsListenerService
    def httpListenerService

    def index() {
        redirect(action:"show", params:params)
    }

    def show() {
    	[activeTape: jmsListenerService.tape]
    }

    def start() {
        def tape = new Tape(name:params.id)
        tape.save(flush:true)
        jmsListenerService.tape = tape
        httpListenerService.tape = tape
        println "Tape [${tape.name}] recording started"

    	render(view:'show', model: [activeTape: tape])
    }

    def stop() {
        jmsListenerService.tape = null
        httpListenerService.tape = null
        println "Recording stopped"

    	render(view:'show', model: [activeTape: null])
    }
}
