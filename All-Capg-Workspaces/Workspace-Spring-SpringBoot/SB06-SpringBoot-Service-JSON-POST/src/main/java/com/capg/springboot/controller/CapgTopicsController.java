package com.capg.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.CapgTopics;
import com.capg.springboot.CapgTopicsService;

@RestController
public class CapgTopicsController {
	@Autowired
	CapgTopicsService topicService;
	@RequestMapping("/myController")
	public String display() {
		return("My controller");
	}
	@RequestMapping("capgtopics")
	public List<CapgTopics> getAllTopics(){
		return topicService.getAllMyTopics();
	}
	@RequestMapping(method=RequestMethod.POST,value="/capgtopics")
	public void addTopics(@RequestBody CapgTopics topic) {
		topicService.addTopics(topic);
	}
}
