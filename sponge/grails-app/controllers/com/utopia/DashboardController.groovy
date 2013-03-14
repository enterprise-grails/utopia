package com.utopia

class DashboardController {

    def index() {
        def d = new Date().clearTime()
        
        def today = LogEvent.where {
            published >= d
        }.count()
        
        def lastWeek = LogEvent.where {
            published < d
            published >= d - 7
        }.count()
        
        def older = LogEvent.where {
            published < d - 7
            published >= d - 30
        }.count()
        
        def mostRecent = LogEvent.list(max:5, sort: "id", order: "desc")
        
        [today: today, lastWeek: lastWeek, older: older, mostRecent: mostRecent]
    }
}
