import com.utopia.*
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        if (!LogEvent.count() && Environment.current != Environment.TEST) {
            def now = new Date()
            5.times {
                new LogEvent(published: now, level: "ERROR", application: "order_router", location: "com.utopia.somePlace", message: "Failed to parse event", exception: "java.util.DateFormatException", trace: "Some trace line 1\nSome trace line 2\nSome trace line 3").save(failOnError:true)
            }
            27.times {
                new LogEvent(published: now - 4, level: "ERROR", application: "order_router", location: "com.utopia.somePlace", message: "Failed to parse event", exception: "java.util.DateFormatException", trace: "Some trace line 1\nSome trace line 2\nSome trace line 3").save(failOnError:true)
            }
            53.times {
                new LogEvent(published: now - 8, level: "ERROR", application: "order_router", location: "com.utopia.somePlace", message: "Failed to parse event", exception: "java.util.DateFormatException", trace: "Some trace line 1\nSome trace line 2\nSome trace line 3").save(failOnError:true)
            }
        }
    }
    def destroy = {
    }
}
