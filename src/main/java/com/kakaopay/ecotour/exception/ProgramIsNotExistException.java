package com.kakaopay.ecotour.exception;

public class ProgramIsNotExistException extends RuntimeException {

	private static final long serialVersionUID = -7614817507849686872L;

	public ProgramIsNotExistException() {
		super();
	}
	
	public ProgramIsNotExistException(String msg) {
		super(msg);
	}
}
