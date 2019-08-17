package com.kakaopay.ecotour.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.kakaopay.ecotour.model.auth.TokenType;
import com.kakaopay.ecotour.provider.JwtTokenProvider;

public class JwtAuthenticationFilter extends GenericFilterBean {

	JwtTokenProvider jwtTokenProvider;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		TokenType type = jwtTokenProvider.getTokenType((HttpServletRequest)request);
		String token = jwtTokenProvider.resolveToken((HttpServletRequest)request);
		if(token != null && jwtTokenProvider.validateToken(type, token)) {
			Authentication auth = jwtTokenProvider.getAuthentication(type, token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}

}
