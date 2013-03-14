
<%@ page import="com.utopia.Tape" %>
<%@ page import="groovy.json.JsonOutput" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tape.label', default: 'Tape')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tape" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tape" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>

			<div>
			
			<ol class="property-list tape">
			
				<g:if test="${tapeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="tape.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${tapeInstance}" field="name"/></span>
					
				</li>
				</g:if>

				<g:if test="${tapeInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="tape.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${tapeInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
			    <h2>Records</h2>
				<g:if test="${tapeInstance?.records}">
					<g:each in="${tapeInstance.records}" var="r">
						<p>
						    <a id="record_${r.id}" href="javascript:void(0)"><g:formatDate date="${r.dateCreated}"/>: ${r.type}</a>
						</p>
						<div id="record-details_${r.id}" style="display:none">
							<li class="fieldcontain">
								<span id="message-label" class="property-label">Message</span>
								<span class="property-value code" aria-labelledby="message-label">${JsonOutput.prettyPrint(r.message).encodeAsPreformatted()}
								</span>
							</li>
		                </div>
		                <r:script>
							$(document).ready(function() {
								$('#record_${r.id}').click(function() {
									$('#record-details_${r.id}').toggle('fast');
								});
							});
						</r:script>
					</g:each>
				</g:if>	
			</ol>

		</div>
	</body>
</html>
