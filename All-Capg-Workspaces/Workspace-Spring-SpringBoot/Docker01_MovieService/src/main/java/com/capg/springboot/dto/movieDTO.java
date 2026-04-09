package com.capg.springboot.dto;

public class movieDTO {
	private String name;
	private double price;
	public movieDTO() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public movieDTO(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}
}
