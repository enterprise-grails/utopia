<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Dashboard</title>
		<r:require module="grailsEvents"/>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div class="modules clearfix">
			<h1>Live Traffic</h1>
			<div class="meters">
				<div class="meter">
					<div id="cur-msgs" class="container">0</div>
				</div>
				<div class="meter output">
					<h2>Payload</h2>
					<div id="msg-payload" class="code">
						none
					</div>
				</div>
			</div>
		</div>
		<r:script>
			var grailsEvents = new grails.Events('${createLink(uri: '')}', {transport:'sse'});
			grailsEvents.on('msgEvent', function(data) {
				$('#cur-msgs').text(parseInt($('#cur-msgs').text()) + 1);
				$('#msg-payload').text(JSON.stringify(JSON.parse(data), undefined, 4));
			});
		</r:script>
		
		<div class="modules clearfix">
			<h1>System Issues</h1>
			<div class="meters">
				<div class="meter">
					<h2>Today</h2>
					<div class="container">${today}</div>
				</div>
				<div class="meter">
					<h2>Last 7 Days</h2>
					<div class="container">${lastWeek}</div>
				</div>
				<div class="meter">
					<h2>Last 30 Days</h2>
					<div class="container">${older}</div>
				</div>
			</div>
		</div>		
		
		<div class="modules">
			<h1>Most Recent Issues &nbsp;&nbsp;<g:link class="small" controller="LogEvent">(all)</g:link></h1>
			
			<div class="content scaffold-list">
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
					<g:each in="${mostRecent}" status="i" var="logEventInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td><g:link controller="logEvent" action="show" id="${logEventInstance.id}">${fieldValue(bean: logEventInstance, field: "level")}</g:link></td>
							<td>${fieldValue(bean: logEventInstance, field: "application")}</td>
							<td>${fieldValue(bean: logEventInstance, field: "location")}</td>
							<td>${fieldValue(bean: logEventInstance, field: "exception")}</td>
						</tr>
					</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
