<%@ page import="com.utopia.*" %>
<%@ page import="groovy.json.JsonOutput" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'order.label', default: 'Order')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-order" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-order" class="content scaffold-show" role="main">
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="col-a">
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
				<ol class="property-list order">
			
					<g:if test="${orderInstance?.customer}">
					<li class="fieldcontain">
						<span id="customer-label" class="property-label"><g:message code="order.customer.label" default="Customer" /></span>
					
							<span class="property-value" aria-labelledby="customer-label"><g:link controller="customer" action="show" id="${orderInstance?.customer?.id}">${orderInstance?.customer?.encodeAsHTML()}</g:link></span>
					
					</li>
					</g:if>
			
					<g:if test="${orderInstance?.cardNumber}">
					<li class="fieldcontain">
						<span id="cardNumber-label" class="property-label"><g:message code="order.cardNumber.label" default="Card Number" /></span>
					
							<span class="property-value" aria-labelledby="cardNumber-label"><g:fieldValue bean="${orderInstance}" field="cardNumber"/></span>
					
					</li>
					</g:if>
			
					<g:if test="${orderInstance?.expiration}">
					<li class="fieldcontain">
						<span id="expiration-label" class="property-label"><g:message code="order.expiration.label" default="Expiration" /></span>
					
							<span class="property-value" aria-labelledby="expiration-label"><g:fieldValue bean="${orderInstance}" field="expiration"/></span>
					
					</li>
					</g:if>
			
					<g:if test="${orderInstance?.dateCreated}">
					<li class="fieldcontain">
						<span id="dateCreated-label" class="property-label"><g:message code="order.dateCreated.label" default="Date Created" /></span>
					
							<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${orderInstance?.dateCreated}" /></span>
					
					</li>
					</g:if>
			
					<g:if test="${orderInstance?.lastUpdated}">
					<li class="fieldcontain">
						<span id="lastUpdated-label" class="property-label"><g:message code="order.lastUpdated.label" default="Last Updated" /></span>
					
							<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${orderInstance?.lastUpdated}" /></span>
					
					</li>
					</g:if>
			
					<g:if test="${orderInstance?.lineItems}">
					<h2>Line Items</h2>
					<g:each in="${orderInstance.lineItems}" var="l">
						<g:set var="lineItemInstance" value="${l}" />
						<p><a id="line-item_${l.id}" href="javascript:void(0)">${l?.encodeAsHTML()}</a></p>
						<div id="line-item-details_${l.id}" style="display:none">
							<g:if test="${lineItemInstance?.product}">
							<li class="fieldcontain">
								<span id="product-label" class="property-label"><g:message code="lineItem.product.label" default="Product" /></span>

									<span class="property-value" aria-labelledby="product-label"><g:link controller="product" action="show" id="${lineItemInstance?.product?.id}">${lineItemInstance?.product?.encodeAsHTML()}</g:link></span>

							</li>
							</g:if>

							<g:if test="${lineItemInstance?.price}">
							<li class="fieldcontain">
								<span id="price-label" class="property-label"><g:message code="lineItem.price.label" default="Price" /></span>

									<span class="property-value" aria-labelledby="price-label"><g:fieldValue bean="${lineItemInstance}" field="price"/></span>

							</li>
							</g:if>

							<g:if test="${lineItemInstance?.quantity}">
							<li class="fieldcontain">
								<span id="quantity-label" class="property-label"><g:message code="lineItem.quantity.label" default="Quantity" /></span>

									<span class="property-value" aria-labelledby="quantity-label"><g:fieldValue bean="${lineItemInstance}" field="quantity"/></span>

							</li>
							</g:if>
						</div>
						<r:script>
							$(document).ready(function() {
								$('#line-item_${l.id}').click(function() {
									$('#line-item-details_${l.id}').toggle('fast');
								});
							});
						</r:script>
					</g:each>
					</g:if>
			
					<g:if test="${orderInstance?.lifeCycleEvents}">
					<h2>Life Cycle Events</h2>
					<div class="life-cycle-event-list">
						<g:each in="${orderInstance.lifeCycleEvents}" var="l">
							<g:set var="lifeCycleEventInstance" value="${l}" />
							<div class="life-cycle-event">
								<p><a id="life-cycle-event_${l.id}" href="javascript:void(0)">${l?.encodeAsHTML()}</a></p>
								<div id="life-cycle-event-details_${l.id}" class="life-cycle-event-details" style="display:none">
									<g:if test="${lifeCycleEventInstance?.referenceId}">
									<li class="fieldcontain">
										<span id="referenceId-label" class="property-label"><g:message code="lifeCycleEvent.referenceId.label" default="Reference ID" /></span>

											<span class="property-value" aria-labelledby="referenceId-label"><a class="ref-id" href="${createLink(controller:'ref')}${lifeCycleEventInstance.referenceId}"><g:fieldValue bean="${lifeCycleEventInstance}" field="referenceId"/></a></span>

									</li>
									</g:if>

									<g:if test="${lifeCycleEventInstance?.dateCreated}">
									<li class="fieldcontain">
										<span id="dateCreated-label" class="property-label">Date</span>

											<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${lifeCycleEventInstance?.dateCreated}" /></span>

									</li>
									</g:if>
							
									<g:if test="${lifeCycleEventInstance?.details}">
									<li class="fieldcontain">
										<span id="details-label" class="property-label">Details</span>

											<span class="property-value code" aria-labelledby="details-label">${lifeCycleEventInstance?.details ? JsonOutput.prettyPrint(lifeCycleEventInstance.details).encodeAsPreformatted() : ''}</span>

									</li>
									</g:if>
								</div>
								<r:script>
									$(document).ready(function() {
										$('#life-cycle-event_${l.id}').click(function() {
											$('#life-cycle-event-details_${l.id}').toggle('fast');
										});
									});
								</r:script>
							</div>
						</g:each>
					</div>
					</g:if>
				</ol>
			</div>
			
			<div class="col-b">
				<div class="status-container">
					<h2>Status:</h2>
					<g:if test="${orderInstance?.lifeCycleEvents && ((orderInstance?.lifeCycleEvents as List).last().eventName ==~ /.*Exception/) }">
						<div class="status bad">Exception</div>
					</g:if>
					<g:else>
						<div class="status">OK</div>
					</g:else>
				</div>
			</div>

			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${orderInstance?.id}" />
					<g:link class="edit" action="edit" id="${orderInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
