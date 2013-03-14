<%@ page import="com.utopia.Order" %>
<%@ page import="groovy.json.JsonOutput" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Web Service Test Console</title>
	</head>
	<body>

		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
	
		<div id="create-ocs-order" class="content scaffold-create" role="main">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:if test="${resp}">
				<h1>Response</h1>
				<div class="status-container clearfix">
					<div class="col-a">
						<div class="status ${resp.status != 200 ? 'bad' : ''}">${resp.status}</div>
					</div>
					<div class="col-b">
						<div id="response-data" class="code">
							<g:if test="${resp.data}">
								${resp.data.toString(4).encodeAsPreformatted()}
							</g:if>
							<g:else>
								n/a
							</g:else>
						</div>
					</div>
				</div>
			</g:if>
			
			<h1>Request</h1>
			<g:hasErrors bean="${orderInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${orderInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<g:form action="invoke" method="post">
				<fieldset class="form">
					<div class="fieldcontain">
						<label for="customer">Service</label>
						<g:select id="service" name="service" from="['OCS', 'Billing', 'Fulfillment', 'Notification', 'Event']" value="${params.service}" />
					</div>
					
					<div class="fieldcontain required">
						<label for="customer">Message (JSON)</label>
						<g:textArea id="payload" name="payload" value='${params.payload}' />
					</div>
				</fieldset>
				<r:script>
					$(document).ready(function() {
						$('#service').change(function() {
							$('#payload').val(JSON.stringify(eval("payloads." + $('#service').val()), undefined, 4))
						})
						
						if (!($('#payload').val())) $('#service').change();
					})
				</r:script>
				<fieldset class="buttons">
					Count: <g:field name="count" value="${params.count ?: 1}" />
					Delay (ms): <g:field name="delay" value="${params.delay ?: ''}" />
					<g:submitButton name="post" class="save" value="Post!" />
				</fieldset>
			</g:form>
		</div>
		<r:script>
			var payloads = {
				"OCS":{
					"customer": {
						"city": "Any Town",
						"email": "jjohnson@me.com",
						"firstName": "Joe",
						"lastName": "Johnson",
						"state": "CT",
						"street": "123 Any Street",
						"zip": "06905"
					},
					"cardNumber": "4111111111111111",
					"expiration": "10/15",
					"lineItems":[
						{
							"price": 19.95,
							"product": {
								"id": 1,
							},
							"quantity": 1
						},
						{
							"price": 12.95,
							"product": {
								"id": 2,
							},
							"quantity": 1
						}
					]
				},
				"Billing":{
					"referenceId": "_order_id_",
					"cardNumber": "4111111111111111",
					"expiration": "10/15",
					"amount": 16.99
				},
				"Fulfillment":{
					"referenceId": "_order_id_",
					"sku": "112233AAA",
					"vendor": "TIME",
					"name": "Joe Doe",
					"address": "225 High Ridge Rd, Stamford, CT, 06905"
				},
				"Notification":{
					"referenceId": "_order_id_",
					"email": "someone@somewhere.com",
					"subject": "Your Order Has Shipped",
					"body": "We thought you'd like to know that your order has shipped."
				},
				"Event":{
				  "eventName": "ChargeAuthorized",
				  "referenceId": "/order/123/simple-merch-order/123/charge/456",
				  "published": "2012-06-15T15:39:06Z",
				  "message": "Charge successfully authorized"
				}
			};
		</r:script>
	</body>
</html>
