<%@ page import="com.utopia.FulfillmentStatus" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Fulfilment Service Control</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list">Fulfillment List</g:link></li>
			</ul>
		</div>
		<div id="content" class="content" role="main">
			<h1>Fulfillment Service Control</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:form method="post" >
				<fieldset class="form">
                    <div class="fieldcontain false">
                        <label for="id">Real-time Response:</label>
	                    <g:select name="id" 
	                        from="${FulfillmentStatus?.values()}" 
	                        keys="${FulfillmentStatus.values()*.name()}" 
	                        value="${realtimeResponse.name()}"/>
                    </div>    
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="control" value="Change" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
