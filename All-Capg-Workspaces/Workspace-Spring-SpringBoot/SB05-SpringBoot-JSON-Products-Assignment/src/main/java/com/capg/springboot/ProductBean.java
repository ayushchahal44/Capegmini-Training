package com.capg.springboot;

import java.io.Serializable;

public class ProductBean implements Serializable{
	private String pid;
	private String pname;
	private int price;
	public ProductBean(String pid,String pname, int price) {
		this.pid = pid;
		this.pname=pname;
		this.price=price;
	}
	public String getPid() {
		return pid;
	}
	public String getPname() {
		return pname;
	}
	public int getPrice() {
		return price;
	}
	
}
