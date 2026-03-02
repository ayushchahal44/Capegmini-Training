package com.capg.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaTopicsController {
	/*
	JavaTopicsService topicsService = new JavaTopicsService();
	<bean id="topicsSerive" class="JavaTopicsController">
	*/
	
	@Autowired
	JavaTopicsService topicsService;
	
	@RequestMapping("login.spring")
	public String loginValid() {
		return "Welcome to SpringBoot Application";
	}
	
	@RequestMapping("/javatopics")
		public List getAllTopics() {
			return topicsService.getAllMyTopics();
		}
}