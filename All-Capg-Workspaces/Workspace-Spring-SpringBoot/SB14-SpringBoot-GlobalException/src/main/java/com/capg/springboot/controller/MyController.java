package com.capg.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.exceptions.MyCustomException;

@RestController
public class MyController {
	@GetMapping("/example/{bid}")
	public ResponseEntity<String> example(@PathVariable("bid") boolean bid){
		boolean someCondition = bid;
		System.out.println(someCondition);
		if(someCondition) {
			throw new MyCustomException("This is a custom Exception");
		}
		return ResponseEntity.ok("Request is processing...");
	}
}
