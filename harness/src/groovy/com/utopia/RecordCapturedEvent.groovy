package com.utopia

import org.springframework.context.ApplicationEvent

class RecordCapturedEvent extends ApplicationEvent {

	public RecordCapturedEvent(Object source) {
		super(source)
	}
}