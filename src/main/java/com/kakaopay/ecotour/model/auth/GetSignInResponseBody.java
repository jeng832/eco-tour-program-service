package com.kakaopay.ecotour.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSignInResponseBody {
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	public GetSignInResponseBody(String access, String refresh) {
		this.accessToken = access;
		this.refreshToken = refresh;
	}
}
