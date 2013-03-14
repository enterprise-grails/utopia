package com.utopia;

class ServiceBridge {

	private static ServiceBridge singleton = new ServiceBridge();

	private HttpListenerService service;

	private ServiceBridge() {
	}

	public static ServiceBridge getInstance() {
		return singleton;
	}

	public void setService(HttpListenerService service) {
		this.service = service;
	}

	public HttpListenerService getService() {
		return this.service;
	}
}