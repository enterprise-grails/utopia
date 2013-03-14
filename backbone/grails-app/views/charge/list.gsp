
<%@ page import="com.utopia.Charge" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'charge.label', default: 'Charge')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-charge" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><g:link class="list" action="control">Control</g:link></li>
			</ul>
		</div>
		<div id="list-charge" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="referenceId" title="${message(code: 'charge.referenceId.label', default: 'Reference Id')}" />
					
						<g:sortableColumn property="cardNumber" title="${message(code: 'charge.cardNumber.label', default: 'Card Number')}" />
					
						<g:sortableColumn property="expiration" title="${message(code: 'charge.expiration.label', default: 'Expiration')}" />
					
						<g:sortableColumn property="amount" title="${message(code: 'charge.amount.label', default: 'Amount')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'charge.status.label', default: 'Status')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${chargeInstanceList}" status="i" var="chargeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${chargeInstance.id}">${fieldValue(bean: chargeInstance, field: "referenceId")}</g:link></td>
					
						<td>${fieldValue(bean: chargeInstance, field: "cardNumber")}</td>
					
						<td>${fieldValue(bean: chargeInstance, field: "expiration")}</td>
					
						<td>${fieldValue(bean: chargeInstance, field: "amount")}</td>
					
						<td>${fieldValue(bean: chargeInstance, field: "status")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${chargeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
