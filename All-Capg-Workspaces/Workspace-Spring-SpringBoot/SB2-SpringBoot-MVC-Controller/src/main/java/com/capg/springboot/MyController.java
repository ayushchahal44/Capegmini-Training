package com.capg.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	@RequestMapping("login.spring")
	public String loginValid() {
		return "Welcome to SpringBoot Application";
	}
	@RequestMapping("ayush.spring")
	public String ayushValid() {
		return "Welcome to SpringBoot Application Hello Ayush";
	}
}
