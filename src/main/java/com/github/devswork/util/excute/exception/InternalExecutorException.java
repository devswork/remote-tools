package com.github.devswork.util.excute.exception;

public class InternalExecutorException extends RuntimeException {
	private static final long serialVersionUID = 2400444291334773579L;

	public InternalExecutorException() { }

	public InternalExecutorException(String message) {
		super(message);
	}

	public InternalExecutorException(String message, Throwable cause) {
		super(message, cause);
	}
}