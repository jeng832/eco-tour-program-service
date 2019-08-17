package com.kakaopay.ecotour.model.auth;

public enum TokenType {
	ACCESS_TOKEN(0),
	REFRESH_TOKEN(1);
	
	private int type;
	
	TokenType(int type) {
		this.type = type;
	}
	
	public int getValue() {
		return this.type;
	}
}
