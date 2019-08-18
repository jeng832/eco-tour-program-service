package com.kakaopay.ecotour.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.ecotour.model.auth.GetSignInResponseBody;
import com.kakaopay.ecotour.service.AuthService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	AuthService authService;

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public ResponseEntity<GetSignInResponseBody> signin(@RequestParam String id, @RequestParam String password) {
		logger.info("signin id: " + id);
		GetSignInResponseBody body = authService.signin(id, password);
		
		return new ResponseEntity<GetSignInResponseBody>(body, HttpStatus.OK);
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public ResponseEntity<?> signup(@RequestParam String id, @RequestParam String password) {
		logger.info("signin id: " + id);
		authService.signup(id, password);
		
		return ResponseEntity.ok().build();
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", required = true, dataType = "String", paramType = "header")
	})
	@RequestMapping(value="/refresh", method=RequestMethod.GET)
	public ResponseEntity<String> refresh(@RequestHeader(value = "Authorization") String authHeader
			, @RequestParam String id, @RequestParam String password) {
		logger.info("refresh id: " + id);
		String body = authService.refresh(authHeader, id, password);
		
		return new ResponseEntity<String>(body, HttpStatus.OK);
	}
}
