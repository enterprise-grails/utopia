package com.utopia

class RoutingController {

    static RoutingType currentRouting = RoutingType.Asynchronous

    def index() {
        redirect(action: "control", params: params)
    }

    def control() {
        if (params.id) {
            currentRouting = RoutingType.valueOf(params.id)
            println "Current routing: ${currentRouting.name().toLowerCase()}"
        }
        [currentRouting: currentRouting]
    }
}
