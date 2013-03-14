
<%@ page import="com.utopia.Charge" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'charge.label', default: 'Charge')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-charge" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-charge" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list charge">
			
				<g:if test="${chargeInstance?.referenceId}">
				<li class="fieldcontain">
					<span id="referenceId-label" class="property-label"><g:message code="charge.referenceId.label" default="Reference Id" /></span>
					
						<span class="property-value" aria-labelledby="referenceId-label"><g:fieldValue bean="${chargeInstance}" field="referenceId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${chargeInstance?.cardNumber}">
				<li class="fieldcontain">
					<span id="cardNumber-label" class="property-label"><g:message code="charge.cardNumber.label" default="Card Number" /></span>
					
						<span class="property-value" aria-labelledby="cardNumber-label"><g:fieldValue bean="${chargeInstance}" field="cardNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${chargeInstance?.expiration}">
				<li class="fieldcontain">
					<span id="expiration-label" class="property-label"><g:message code="charge.expiration.label" default="Expiration" /></span>
					
						<span class="property-value" aria-labelledby="expiration-label"><g:fieldValue bean="${chargeInstance}" field="expiration"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${chargeInstance?.amount}">
				<li class="fieldcontain">
					<span id="amount-label" class="property-label"><g:message code="charge.amount.label" default="Amount" /></span>
					
						<span class="property-value" aria-labelledby="amount-label"><g:fieldValue bean="${chargeInstance}" field="amount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${chargeInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="charge.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${chargeInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${chargeInstance?.id}" />
					<g:link class="edit" action="edit" id="${chargeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
