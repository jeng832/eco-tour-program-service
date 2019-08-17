package com.kakaopay.ecotour.exception;

public class SignInFailedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8314863713261214420L;

	public SignInFailedException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public SignInFailedException() {
		super();
	}
}
