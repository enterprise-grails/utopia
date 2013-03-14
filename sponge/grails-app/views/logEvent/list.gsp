
<%@ page import="com.utopia.LogEvent" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'logEvent.label', default: 'LogEvent')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-logEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="home" controller="dashboard">Dashboard</g:link></li>
			</ul>
		</div>
		<div id="list-logEvent" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="level" title="${message(code: 'logEvent.level.label', default: 'Level')}" />
					
						<g:sortableColumn property="application" title="${message(code: 'logEvent.application.label', default: 'Application')}" />
					
						<g:sortableColumn property="location" title="${message(code: 'logEvent.location.label', default: 'Location')}" />
					
						<g:sortableColumn property="exception" title="${message(code: 'logEvent.exception.label', default: 'Exception')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${logEventInstanceList}" status="i" var="logEventInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${logEventInstance.id}">${fieldValue(bean: logEventInstance, field: "level")}</g:link></td>
					
						<td>${fieldValue(bean: logEventInstance, field: "application")}</td>
					
						<td>${fieldValue(bean: logEventInstance, field: "location")}</td>
					
						<td>${fieldValue(bean: logEventInstance, field: "exception")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${logEventInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
