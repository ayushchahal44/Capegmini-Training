package com.capg.springboot.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="orderTable")
public class OrderTable {
	
	@Id
	private String orderId;
	private String orderStatus;
	private LocalDate orderDate;
	
	@OneToOne(mappedBy = "orderTable",fetch = FetchType.LAZY, cascade =CascadeType.ALL) 
	private Customer customer;
	
	@OneToMany(mappedBy = "orderTable",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Product> products;

	@OneToOne(mappedBy="orderTable",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Address address;
	
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	
	

}
