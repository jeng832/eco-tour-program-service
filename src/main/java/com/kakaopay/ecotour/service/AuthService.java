package com.kakaopay.ecotour.service;

import com.kakaopay.ecotour.model.auth.GetSignInResponseBody;

public interface AuthService {

	GetSignInResponseBody signin(String id, String password);
	void signup(String id, String password);
	String refresh(String authHeader, String id, String password);
}
