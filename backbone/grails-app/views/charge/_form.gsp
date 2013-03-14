<%@ page import="com.utopia.Charge" %>



<div class="fieldcontain ${hasErrors(bean: chargeInstance, field: 'referenceId', 'error')} required">
	<label for="referenceId">
		<g:message code="charge.referenceId.label" default="Reference Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="referenceId" required="" value="${chargeInstance?.referenceId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: chargeInstance, field: 'cardNumber', 'error')} required">
	<label for="cardNumber">
		<g:message code="charge.cardNumber.label" default="Card Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cardNumber" required="" value="${chargeInstance?.cardNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: chargeInstance, field: 'expiration', 'error')} required">
	<label for="expiration">
		<g:message code="charge.expiration.label" default="Expiration" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="expiration" required="" value="${chargeInstance?.expiration}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: chargeInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="charge.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="amount" step="any" min="0.01" required="" value="${chargeInstance.amount}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: chargeInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="charge.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${com.utopia.BillingStatus?.values()}" keys="${com.utopia.BillingStatus.values()*.name()}" required="" value="${chargeInstance?.status?.name()}"/>
</div>

