modules = {
    application {
        defaultBundle 'monolith'
        dependsOn 'jquery'
        resource url:'js/application.js'
    }
    
    ui {
        defaultBundle 'monolith'
        dependsOn 'jquery'
        resource url:'css/main.css'
        resource url:'css/mobile.css'
    }

    overrides {
        jquery {
            defaultBundle 'monolith'
        }
    }
}