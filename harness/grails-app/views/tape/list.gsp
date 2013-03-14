
<%@ page import="com.utopia.Tape" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tape.label', default: 'Tape')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tape" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="list-tape" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="name" title="${message(code: 'tape.name.label', default: 'Name')}" />
						<g:sortableColumn property="dateCreated" title="${message(code: 'tape.dateCreated.label', default: 'Date Created')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${tapeInstanceList}" status="i" var="tapeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
						    <g:link action="show" id="${tapeInstance.id}">${fieldValue(bean: tapeInstance, field: "name")}</g:link>
						</td>
						<td>
						    <span><g:formatDate date="${tapeInstance?.dateCreated}" /></span>
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tapeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
