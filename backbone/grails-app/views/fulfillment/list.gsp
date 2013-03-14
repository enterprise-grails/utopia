<%@ page import="com.utopia.Fulfillment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fulfillment.label', default: 'Fulfillment')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-fulfillment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li><g:link class="list" action="control">Control</g:link></li>
			</ul>
		</div>
		<div id="list-fulfillment" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="referenceId" title="${message(code: 'fulfillment.referenceId.label', default: 'Reference Id')}" />
					
						<g:sortableColumn property="sku" title="${message(code: 'fulfillment.sku.label', default: 'Sku')}" />
					
						<g:sortableColumn property="vendor" title="${message(code: 'fulfillment.vendor.label', default: 'Vendor')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'fulfillment.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="address" title="${message(code: 'fulfillment.address.label', default: 'Address')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'fulfillment.status.label', default: 'Status')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fulfillmentInstanceList}" status="i" var="fulfillmentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${fulfillmentInstance.id}">${fieldValue(bean: fulfillmentInstance, field: "referenceId")}</g:link></td>
					
						<td>${fieldValue(bean: fulfillmentInstance, field: "sku")}</td>
					
						<td>${fieldValue(bean: fulfillmentInstance, field: "vendor")}</td>
					
						<td>${fieldValue(bean: fulfillmentInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: fulfillmentInstance, field: "address")}</td>
					
						<td>${fieldValue(bean: fulfillmentInstance, field: "status")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fulfillmentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
