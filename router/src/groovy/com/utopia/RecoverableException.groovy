package com.utopia

class RecoverableException extends RuntimeException {

	public RecoverableException() {
		super()
	}

	public RecoverableException(String m) {
		super(m)
	}

	public RecoverableException(String m, Throwable t) {
		super(m, t)
	}

	public RecoverableException(Throwable t) {
		super(t)
	}
}