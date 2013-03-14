<%@ page import="com.utopia.Order" %>



<div class="fieldcontain ${hasErrors(bean: orderInstance, field: 'cardNumber', 'error')} ">
	<label for="cardNumber">
		<g:message code="order.cardNumber.label" default="Card Number" />
		
	</label>
	<g:textField name="cardNumber" value="${orderInstance?.cardNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orderInstance, field: 'expiration', 'error')} ">
	<label for="expiration">
		<g:message code="order.expiration.label" default="Expiration" />
		
	</label>
	<g:textField name="expiration" value="${orderInstance?.expiration}"/>
</div>

