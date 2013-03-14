package com.utopia

class NotificationController {

    def eventService
    def notificationService

    def save() {
        def refId = notificationService.process(
                referenceId: request.JSON.referenceId,
                email: request.JSON.email,
                subject: request.JSON.subject,
                body: request.JSON.body
        )
        
        render(contentType: "application/json") {
            id = "${refId}"
        }
    }
}
