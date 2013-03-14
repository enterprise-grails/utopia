<%@ page import="com.utopia.Fulfillment" %>



<div class="fieldcontain ${hasErrors(bean: fulfillmentInstance, field: 'referenceId', 'error')} required">
	<label for="referenceId">
		<g:message code="fulfillment.referenceId.label" default="Reference Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="referenceId" required="" value="${fulfillmentInstance?.referenceId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fulfillmentInstance, field: 'sku', 'error')} required">
	<label for="sku">
		<g:message code="fulfillment.sku.label" default="Sku" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="sku" required="" value="${fulfillmentInstance?.sku}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fulfillmentInstance, field: 'vendor', 'error')} required">
	<label for="vendor">
		<g:message code="fulfillment.vendor.label" default="Vendor" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="vendor" required="" value="${fulfillmentInstance?.vendor}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fulfillmentInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="fulfillment.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${fulfillmentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fulfillmentInstance, field: 'address', 'error')} required">
	<label for="address">
		<g:message code="fulfillment.address.label" default="Address" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="address" required="" value="${fulfillmentInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: fulfillmentInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="fulfillment.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${com.utopia.FulfillmentStatus?.values()}" keys="${com.utopia.FulfillmentStatus.values()*.name()}" required="" value="${fulfillmentInstance?.status?.name()}"/>
</div>

