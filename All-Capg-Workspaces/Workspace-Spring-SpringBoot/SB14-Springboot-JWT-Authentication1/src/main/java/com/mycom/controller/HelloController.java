package com.mycom.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@GetMapping("/hello")
	public String sayHello() {

		return "Welcome to Spring Security JWT"; 
	}
	
	@GetMapping("/myData")
	public String myData(HttpServletRequest request) {

		return " Welcome to Spring Security JWT  : "+request.getSession().getId(); 
	}
	
	
}
