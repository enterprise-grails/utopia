
<%@ page import="com.utopia.Fulfillment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fulfillment.label', default: 'Fulfillment')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-fulfillment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-fulfillment" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list fulfillment">
			
				<g:if test="${fulfillmentInstance?.referenceId}">
				<li class="fieldcontain">
					<span id="referenceId-label" class="property-label"><g:message code="fulfillment.referenceId.label" default="Reference Id" /></span>
					
						<span class="property-value" aria-labelledby="referenceId-label"><g:fieldValue bean="${fulfillmentInstance}" field="referenceId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.sku}">
				<li class="fieldcontain">
					<span id="sku-label" class="property-label"><g:message code="fulfillment.sku.label" default="Sku" /></span>
					
						<span class="property-value" aria-labelledby="sku-label"><g:fieldValue bean="${fulfillmentInstance}" field="sku"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.vendor}">
				<li class="fieldcontain">
					<span id="vendor-label" class="property-label"><g:message code="fulfillment.vendor.label" default="Vendor" /></span>
					
						<span class="property-value" aria-labelledby="vendor-label"><g:fieldValue bean="${fulfillmentInstance}" field="vendor"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="fulfillment.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${fulfillmentInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="fulfillment.address.label" default="Address" /></span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${fulfillmentInstance}" field="address"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="fulfillment.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${fulfillmentInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="fulfillment.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${fulfillmentInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fulfillmentInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="fulfillment.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${fulfillmentInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${fulfillmentInstance?.id}" />
					<g:link class="edit" action="edit" id="${fulfillmentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
