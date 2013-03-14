package com.utopia

import org.apache.log4j.AppenderSkeleton
import org.apache.log4j.spi.LoggingEvent
import org.apache.log4j.Level
import grails.web.JSONBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.exceptions.DefaultStackTracePrinter

class SysBusLogAppender extends AppenderSkeleton {
    
    private final Level threshold
    
    SysBusLogAppender(Level level = Level.WARN) {
        threshold = level
    }
    
	protected void append(LoggingEvent event) {
	    if (!event.level.isGreaterOrEqual(threshold)) return
	    
	    def grailsApplication = ApplicationHolder.application
	    
        try {            
    	    def appName = grailsApplication.metadata['app.name']
    	    def tInfo = event.throwableInformation
	    
            def builder = new JSONBuilder().build() {
            	published = new Date()
                level = event.level.toString()
                application = appName
                location = "${event.loggerName}"
            	message = event.renderedMessage
            	if (tInfo) {
            	    exception = {
            	        type = tInfo.throwable?.class?.name
            	        trace = new DefaultStackTracePrinter().prettyPrint(tInfo.throwable)
            	    }
            	}
            }
                    
            def jmsService = grailsApplication.mainContext.getBean("jmsService")
            def cfg = grailsApplication.config.eventDriven
            
            jmsService.send(topic:cfg.log.topic ?: "sysBus", builder.toString(), cfg.log.template ?: "log") { msg ->
                msg.setStringProperty "level", event.level.toString()
                msg.setStringProperty "application", appName
            }
        } catch (Throwable ex) {
            ex.printStackTrace()
        }     
	}

	@Override
	public void close() {
		if (!closed) closed = true
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}
}
