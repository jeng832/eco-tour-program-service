package com.kakaopay.ecotour.exception.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kakaopay.ecotour.exception.ProgramIsNotExistException;

@ControllerAdvice
public class ApiControllerAdvice {
	
	private Logger logger = LoggerFactory.getLogger(ApiControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<?> handle(MethodArgumentNotValidException e) {
		logger.error("Argument is not valid", e);
		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, "Argument is not valid");	
		return ResponseEntity.badRequest().body(em);	
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<?> handle(HttpMessageNotReadableException e) {
		logger.error("Http message is not readable", e);
		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, "Http message is not readable");	
		return ResponseEntity.badRequest().body(em);	
	}
	
	
	
	@ExceptionHandler(ProgramIsNotExistException.class)
	protected ResponseEntity<?> handle(ProgramIsNotExistException e) {
		logger.error("Program Is Not Exist", e);
		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.badRequest().body(em);
	}
}
