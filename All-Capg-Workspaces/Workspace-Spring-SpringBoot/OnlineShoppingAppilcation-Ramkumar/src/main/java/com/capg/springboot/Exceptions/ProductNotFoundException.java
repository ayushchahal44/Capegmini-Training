package com.capg.springboot.Exceptions;

public class ProductNotFoundException extends Exception {
	public ProductNotFoundException(String s) {
		super(s);
	}
}
