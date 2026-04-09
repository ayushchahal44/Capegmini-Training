package com.capg.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CapgTopicsService {
	public List topics = Arrays.asList(new CapgTopics("SpringBoot","SpringMVC","SPRING"),
			new	CapgTopics("J2SE","JDBC","JAVA"),
			new CapgTopics("UI Technologies","Angular 6","ANGULAR"));
	List<CapgTopics> myTopics = new ArrayList<CapgTopics>(topics);
	public List<CapgTopics> getAllMyTopics(){
		return myTopics;
	}
	public void addTopics(CapgTopics topic) {
		myTopics.add(topic);
	}
}