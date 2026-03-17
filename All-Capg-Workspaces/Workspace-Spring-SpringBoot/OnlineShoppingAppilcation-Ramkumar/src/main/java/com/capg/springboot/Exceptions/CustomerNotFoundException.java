package com.capg.springboot.Exceptions;

public class CustomerNotFoundException extends Exception {
	public CustomerNotFoundException(String s) {
		super(s);
	}
}
