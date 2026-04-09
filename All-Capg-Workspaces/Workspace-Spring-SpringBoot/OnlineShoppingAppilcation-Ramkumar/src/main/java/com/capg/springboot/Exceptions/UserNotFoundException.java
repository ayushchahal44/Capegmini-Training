package com.capg.springboot.Exceptions;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String s) {
		super(s);
	}
}
