// Place your Spring DSL code here
beans = {
    jmsConnectionFactory(org.springframework.jms.connection.SingleConnectionFactory) {
        targetConnectionFactory = { org.apache.activemq.ActiveMQConnectionFactory cf ->
            brokerURL = 'tcp://localhost:61616'
            // redeliveryPolicy = jmsRedeliveryPolicy
        }
        reconnectOnException = true
        clientId = "984f98jwre9"
    }
}
