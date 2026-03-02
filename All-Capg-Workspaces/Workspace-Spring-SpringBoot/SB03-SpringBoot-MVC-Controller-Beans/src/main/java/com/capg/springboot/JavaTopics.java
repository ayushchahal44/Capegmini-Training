package com.capg.springboot;

import java.io.Serializable;

public class JavaTopics implements Serializable{
	private String name;
	private String description;
	private String id;
	public JavaTopics(String name,String description, String id) {
		this.name = name;
		this.description=description;
		this.id=id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getId() {
		return id;
	}	
}
