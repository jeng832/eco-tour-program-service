package com.kakaopay.ecotour.exception.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kakaopay.ecotour.exception.SignInFailedException;
import com.kakaopay.ecotour.exception.TokenRefreshFailedException;

@ControllerAdvice
public class AuthControllerAdvice {
	private Logger logger = LoggerFactory.getLogger(AuthControllerAdvice.class);

	@ExceptionHandler(SignInFailedException.class)
	protected ResponseEntity<?> handle(SignInFailedException e) {
		logger.error("Signin Failed", e);
		ErrorMessage em = new ErrorMessage(HttpStatus.FORBIDDEN, "Signin Failed");	
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(em);	
	}
	
	@ExceptionHandler(TokenRefreshFailedException.class)
	protected ResponseEntity<?> handle(TokenRefreshFailedException e) {
		logger.error("Signin Failed", e);
		ErrorMessage em = new ErrorMessage(HttpStatus.FORBIDDEN, "Token refresh Failed");	
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(em);	
	}
}
