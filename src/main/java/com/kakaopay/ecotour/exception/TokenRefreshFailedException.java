package com.kakaopay.ecotour.exception;

public class TokenRefreshFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6459685210566476650L;

	public TokenRefreshFailedException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public TokenRefreshFailedException() {
		super();
	}
}
