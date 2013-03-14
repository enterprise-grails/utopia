UTOPIA
======

Utopia is a proof-of-concept implementation of an event-driven, service-oriented architecture build entirely on the Grails framework. It implments a very simple workflow for placing an order in a fictitious enterprise environment where each step in the order processing pipeline is implemented in different systems, exposed by web services.

Prerequisites
-------------

* ActiveMQ 5.x running on default ports (tested with 5.6.0)
* Groovy 1.8.x (tested with 1.8.6)
* Grails 2.x (tested with 2.0.4)

Quick Start
-----------

1. Start ActiveMQ. No further configuration needed. The JMS topic is defined automatically on first use.
2. From ./backbone start the backbone app

		grails run-app
		
3. From ./router start the router app
4. From ./simple-merch start the merch process app
5. Bring the backbone app up in a browser at `http://localhost:8080/backbone`
6. From the "Service Console" screen, post a new order to OCS (selected in the drop down)
7. From the "Order" screen click on the order in the list that was just created to view the event history
8. From the "Fulfillment" screen update the status of each pending fulfillment request to "Shipped" and save
9. Refresh the order details screen to see the rest of the event history

Components
----------

The prototype is broken into four individual components each implemented as a Grails application hence residing in a dedicated sub-directory.

1. Backbone [./backbone] simulates back-end services including order capture, billing, fulfillment and notification. It also implements persistent order repository and provides web-based UI for all the functionality. By default it runs on local port 8080. 

2. Router [./router] represents top level decision maker. It ensures that only one part of system is responsible for selecting appropriate workflow for further order processing. Note that the router doesn't process the actual order, it only routes it to the most suitable workflow implementation. By default it runs on local port 8081.

3. Simple Merchandise Workflow [./simple-merch] implements the workflow use case as described above. It is envisioned as an order "owner" responsible for all decisions regarding the order processing including error handling trigger in underlying services and processes. Note that these back-end components will likely still control some decisions within their respective field of expertise. The workflow however is responsible for enforcing the business rules upon the order. By default it runs on local port 8082.

4. Testing and Governance Harness [./harness] is a crude baseline implementation providing simple JMS and HTTP traffic recording capabilities. It is envisioned that this raw data will provide initial input for testing tools (e.g. record and replay mechanics) as well as registry-type application needed to keep track of what events and services have been implemented. The harness itself runs by default on local port 9090. During the startup it spawns a HTTP proxy on hard-coded port 8080 to sniff the traffic between the backbone and the rest of the components. Therefore the backbone needs to be restarted first on port 7070 (using -Dserver.port=xxxx command line attribute).

5. Event-Driven Plugin [./event-driven]

6. Web Service Security UI [./service-security-ui]

7. Sponge Admin Dashboard [./sponge]
