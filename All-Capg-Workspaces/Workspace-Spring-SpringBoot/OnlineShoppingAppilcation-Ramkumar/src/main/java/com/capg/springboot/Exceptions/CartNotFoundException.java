package com.capg.springboot.Exceptions;

public class CartNotFoundException extends Exception{
	public CartNotFoundException(String s) {
		super(s);
	}
}
