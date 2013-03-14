// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

jms {
    templates {
        log {
            connectionFactoryBean = "logConnectionFactory"
            messageConverterBean = "standardJmsMessageConverter"
        }
    }
    containers { 
        durable {
            meta {
                parentBean = 'standardJmsListenerContainer'
            }
            subscriptionDurable = true
            durableSubscriptionName = "utopia-router"
            sessionTransacted = true
        }
    }
}

endpoints {
    order {
        url = 'http://127.0.0.1:8080/backbone/ocs/'
        user = "router"
        passwd = "asdf"
    }
    simple.merch = 'http://127.0.0.1:8082/simple-merch/process/'
}

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    test {
        endpoints {
            /* To run integration tests, add a host mapping for localblah.com to 127.0.0.1 */
            /* This is required because of a bug in Betamax. See http://github.com/robfletcher/betamax/issues/62 */
            order {
                url = 'http://localblah.com:8080/backbone/ocs/'
            }
            simple.merch = 'http://localblah.com:8082/simple-merch/process/'
        }
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    appenders {
       'null' name: "stacktrace"
       appender name:'sysBus', new com.utopia.SysBusLogAppender(org.apache.log4j.Level.WARN)
    }
    
    root {
        error 'stdout', 'sysBus'
    }
}
