<%@ page import="com.utopia.RoutingType" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Routing Control</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="content" class="content scaffold-edit" role="main">
			<h1>Routing Control</h1>
			<g:if test="${flash.message}">
			    <div class="message" role="status">${flash.message}</div>
			</g:if>
		    <g:form method="post">
				<fieldset class="form">
                    <div class="fieldcontain false">
                        <label for="id">Current routing:</label>
	                    <g:select name="id" 
	                        from="${RoutingType?.values()}" 
	                        keys="${RoutingType.values()*.name()}" 
	                        value="${currentRouting.name()}"/>
	                </div>    
	            </fieldset>    
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="control" value="Change" />
				</fieldset>
		    </g:form>
		</div>
	</body>
</html>
