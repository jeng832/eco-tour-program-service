package com.kakaopay.ecotour.exception;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2339838780997416705L;

	public UserNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public UserNotFoundException() {
		super();
	}
}
