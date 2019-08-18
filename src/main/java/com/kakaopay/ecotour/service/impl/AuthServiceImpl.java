package com.kakaopay.ecotour.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.ecotour.exception.SignInFailedException;
import com.kakaopay.ecotour.exception.TokenRefreshFailedException;
import com.kakaopay.ecotour.manager.DataManager;
import com.kakaopay.ecotour.model.auth.GetSignInResponseBody;
import com.kakaopay.ecotour.model.auth.SignInUserData;
import com.kakaopay.ecotour.model.auth.TokenType;
import com.kakaopay.ecotour.provider.JwtTokenProvider;
import com.kakaopay.ecotour.service.AuthService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataManager dataMgr;
	
	@Override
	public GetSignInResponseBody signin(String id, String password) {
		SignInUserData userData = dataMgr.getUserData(id);
		if(!passwordEncoder.matches(password, userData.getEncodedPassword())) {
			throw new SignInFailedException();
		}
		String access = jwtTokenProvider.createToken(id, userData.getRoles());
		String refresh = jwtTokenProvider.refreshToken(id, userData.getRoles());
		return new GetSignInResponseBody(access, refresh);
	}

	@Override
	public void signup(String id, String password) {
		dataMgr.saveUser(id, passwordEncoder.encode(password));
	}

	@Override
	public String refresh(String authHeader, String id, String password) {
		String[] refreshAuthHeader = authHeader.split(" ");
		String token = null;
		if(refreshAuthHeader[0].equals("Bearer")) {
			token = refreshAuthHeader[1];
		}
		if(token != null && jwtTokenProvider.validateToken(TokenType.REFRESH_TOKEN, token)) {
			SignInUserData userData = dataMgr.getUserData(id);
			
			if(!passwordEncoder.matches(password, userData.getEncodedPassword())) {
				throw new TokenRefreshFailedException();
			}
			
			return jwtTokenProvider.createToken(id, userData.getRoles());
		}
		throw new TokenRefreshFailedException();
	}

}
