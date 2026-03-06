package com.capg.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MyCustomException.class)
	public ResponseEntity<String> handleCustomException(MyCustomException ex){
		return new ResponseEntity<>("Custom Exception: "+ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex){
		return new ResponseEntity<>("An error occurred: "+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
