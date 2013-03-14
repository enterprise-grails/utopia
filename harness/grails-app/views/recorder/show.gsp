
<%@ page import="com.utopia.Tape" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tape.label', default: 'Tape')}" />
		<title>Recorder Control</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div class="content scaffold-show" role="main">
			<h1>Recorder Control</h1>
			<g:if test="${flash.message}">
			    <div class="message" role="status">${flash.message}</div>
			</g:if>

			<g:form method="post">
				<fieldset class="form">
                    <div class="fieldcontain">
	                    <label for="name">Tape Name</label>
				        <g:if test="${activeTape}">
					        <span>${activeTape.name}</span>
					    </g:if>    
	                    <g:else>
	                        <g:textField name="id" value="" />
	                    </g:else>    
                    </div>
				</fieldset>
				<fieldset class="buttons">
				    <g:if test="${activeTape}">
					    <g:actionSubmit class="edit" action="stop" value="Stop recording" />
					</g:if>
					<g:else>
					    <g:actionSubmit class="edit" action="start" value="Start recording" />
					</g:else>    
				</fieldset>
            </g:form>
		</div>
	</body>
</html>
