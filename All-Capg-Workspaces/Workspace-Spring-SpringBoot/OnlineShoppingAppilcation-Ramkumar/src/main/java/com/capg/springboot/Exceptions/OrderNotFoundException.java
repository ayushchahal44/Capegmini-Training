package com.capg.springboot.Exceptions;

public class OrderNotFoundException extends Exception {
	public OrderNotFoundException(String s) {
		super(s);
	}
}
