
<%@ page import="com.utopia.LogEvent" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'logEvent.label', default: 'LogEvent')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-logEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="home" controller="dashboard">Dashboard</g:link></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-logEvent" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list logEvent">
			
				<g:if test="${logEventInstance?.published}">
				<li class="fieldcontain">
					<span id="published-label" class="property-label"><g:message code="logEvent.published.label" default="Published" /></span>
					
						<span class="property-value" aria-labelledby="published-label"><g:formatDate date="${logEventInstance?.published}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="logEvent.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${logEventInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.level}">
				<li class="fieldcontain">
					<span id="level-label" class="property-label"><g:message code="logEvent.level.label" default="Level" /></span>
					
						<span class="property-value" aria-labelledby="level-label"><g:fieldValue bean="${logEventInstance}" field="level"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.application}">
				<li class="fieldcontain">
					<span id="application-label" class="property-label"><g:message code="logEvent.application.label" default="Application" /></span>
					
						<span class="property-value" aria-labelledby="application-label"><g:fieldValue bean="${logEventInstance}" field="application"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.location}">
				<li class="fieldcontain">
					<span id="location-label" class="property-label"><g:message code="logEvent.location.label" default="Location" /></span>
					
						<span class="property-value" aria-labelledby="location-label"><g:fieldValue bean="${logEventInstance}" field="location"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.message}">
				<li class="fieldcontain">
					<span id="message-label" class="property-label"><g:message code="logEvent.message.label" default="Message" /></span>
					
						<span class="property-value" aria-labelledby="message-label"><g:fieldValue bean="${logEventInstance}" field="message"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.exception}">
				<li class="fieldcontain">
					<span id="exception-label" class="property-label"><g:message code="logEvent.exception.label" default="Exception" /></span>
					
						<span class="property-value" aria-labelledby="exception-label"><g:fieldValue bean="${logEventInstance}" field="exception"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${logEventInstance?.trace}">
				<li class="fieldcontain">
					<span id="trace-label" class="property-label"><g:message code="logEvent.trace.label" default="Trace" /></span>
					
						<span class="property-value" aria-labelledby="trace-label"><g:fieldValue bean="${logEventInstance}" field="trace"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${logEventInstance?.id}" />
					<g:link class="edit" action="edit" id="${logEventInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
