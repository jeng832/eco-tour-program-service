package com.kakaopay.ecotour.provider;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.kakaopay.ecotour.model.auth.TokenType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenProvider {
	@Value("spring.jwt.access.secret")
	private String accessTokenSecretKey;
	@Value("spring.jwt.refresh.secret")
	private String refreshTokenSecretKey;

	private long accessTokenValidInMs = 1000L * 60 * 5;   //1 min
	private long refreshTokenValidInMs = 1000L * 60 * 20;   //10 min
	private final UserDetailsService userDetailsService;
	
	JwtTokenProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@PostConstruct
	protected void init() {
		accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes());
	}
	
	
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + accessTokenValidInMs))
				.signWith(SignatureAlgorithm.HS256, accessTokenSecretKey)
				.compact();
	}
	
	public String refreshToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + refreshTokenValidInMs))
				.signWith(SignatureAlgorithm.HS256, refreshTokenSecretKey)
				.compact();
	}
	
	
	public Authentication getAuthentication(TokenType type, String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(type, token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	
	public String getUserPk(TokenType type, String token) {
		String secretKey = (type == TokenType.ACCESS_TOKEN) ? this.accessTokenSecretKey : this.refreshTokenSecretKey;
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		
	}
	
	
	public String resolveToken(HttpServletRequest req) {
		if(req.getRequestURI().equals("/auth/refresh")) {
			String[] refreshAuthHeader = req.getHeader("Authentication").split(" ");
			if(refreshAuthHeader[0].equals("Bearer")) {
				return refreshAuthHeader[1];
			}
			return null;
		}
		return req.getHeader("X-AUTH-TOKEN");
	}
	
	public TokenType getTokenType(HttpServletRequest req) {
		if(req.getRequestURI().equals("/auth/refresh")) {
			String[] refreshAuthHeader = req.getHeader("Authentication").split(" ");
			if(refreshAuthHeader[0].equals("Bearer")) {
				return TokenType.REFRESH_TOKEN;
			}
			return null;
		}
		return TokenType.ACCESS_TOKEN;
	}
	
	
	public boolean validateToken(TokenType type, String jwtToken) {
		String secretKey = (type == TokenType.ACCESS_TOKEN) ? this.accessTokenSecretKey : this.refreshTokenSecretKey;
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
