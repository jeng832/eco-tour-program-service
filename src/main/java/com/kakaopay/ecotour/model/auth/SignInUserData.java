package com.kakaopay.ecotour.model.auth;

import java.util.List;

public class SignInUserData {
	private String encodedPassword;
	private List<String> roles;
	
	public SignInUserData(String encodedPassword, List<String> roles) {
		this.encodedPassword = encodedPassword;
		this.roles = roles;
	}
	
	public String getEncodedPassword() {
		return this.encodedPassword;
	}
	
	public List<String> getRoles() {
		return this.roles;
	}
}
