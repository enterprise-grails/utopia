package com.utopia

class NotificationService {
    
    def eventService

    String process(args) {
        def notificationId = Util.unique[0..5]
        def refId = "${args.referenceId}/notification/${notificationId}"
        eventService.publish(eventName: "NotificationSent", referenceId: refId)
        return refId
    }
}
