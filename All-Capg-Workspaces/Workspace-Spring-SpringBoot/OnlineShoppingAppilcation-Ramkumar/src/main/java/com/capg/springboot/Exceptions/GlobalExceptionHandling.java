package com.capg.springboot.Exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
@ControllerAdvice
public class GlobalExceptionHandling {
	    
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Validation Failed ..." + exception);
	}
		
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(HttpMessageNotReadableException exception) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Validation Failed ..." + exception);
	}
		
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(MethodArgumentTypeMismatchException exception) {
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Validation Failed ..." + exception);
	}
		
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(DataAccessException exception) {
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data Validation Failed ..." + exception);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(UserNotFoundException exception) {
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(ProductNotFoundException exception) {
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(AddressNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(AddressNotFoundException exception) {
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(OrderNotFoundException exception) {
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Object> handleValidationException(CustomerNotFoundException exception) {
	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	}
