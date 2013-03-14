beans = {

    // ==============================
    // JMS Configuration via ActiveMQ
    // ==============================

    jmsRedeliveryPolicy(org.apache.activemq.RedeliveryPolicy){
        maximumRedeliveries = 3
        initialRedeliveryDelay = 30000L
        redeliveryDelay = 30000L
    }

    jmsConnectionFactory(org.springframework.jms.connection.SingleConnectionFactory) {
        targetConnectionFactory = { org.apache.activemq.ActiveMQConnectionFactory cf ->
            brokerURL = 'tcp://localhost:61616'
            redeliveryPolicy = jmsRedeliveryPolicy
        }
        reconnectOnException = true
        clientId = "ja9f8j49w8r"
    }

    // ==================================================
    // JMS Configuration via Weblogic
    // (see readme.txt for JMS provider swapping details)
    // ==================================================

    /*
     * Technically only jndiTemplate and jmsConnectionFactory beans below are required 
     * if the WLS topic is declared directly with its full name (i.e. without using JNDI). 
     * In such case however the topic name within the source code would need to be changed 
     * from 'msgBus' to the path composed of actual WLS artifact names used in JMS setup, 
     * e.g. 'MsgBusServer/MsgBusModule!MsgBusTopic'. If topic JNDI loojup is used (i.e. the 
     * full setup below is enabled), the JNDI topic name in WLS should be 'msgBus' to avoid 
     * source code changes. Note that the destination name in the standardJmsListenerContainer 
     * is a required property and is in fact overwritten by the destination name defined 
     * within the listener class.
     */

    /* 
    jndiTemplate(org.springframework.jndi.JndiTemplate) {
        environment = [
            "java.naming.factory.initial" : "weblogic.jndi.WLInitialContextFactory",
            "java.naming.provider.url" : "t3://localhost:10301/",
            "java.naming.security.principal" : "system",
            "java.naming.security.credentials" : "webl5342"
        ]
    }

    jmsConnectionFactory(org.springframework.jndi.JndiObjectFactoryBean) {
        jndiTemplate = ref(jndiTemplate)
        jndiName = 'javax/jms/TopicConnectionFactory'
    }

    jmsDestinationResolver(org.springframework.jms.support.destination.JndiDestinationResolver) {
        jndiTemplate = ref(jndiTemplate)
        cache = true        
    }

    standardJmsTemplate(org.springframework.jms.core.JmsTemplate) {
        connectionFactory = ref(jmsConnectionFactory)
        destinationResolver = ref(jmsDestinationResolver)
    }

    standardJmsListenerContainer(org.springframework.jms.listener.DefaultMessageListenerContainer) {
        connectionFactory = ref(jmsConnectionFactory)
        destinationResolver = ref(jmsDestinationResolver)
        destinationName = 'default'
    }
    */
}
