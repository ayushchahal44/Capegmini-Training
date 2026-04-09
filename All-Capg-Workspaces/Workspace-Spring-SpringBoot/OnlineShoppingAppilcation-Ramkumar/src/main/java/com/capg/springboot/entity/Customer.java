package com.capg.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="customer")
public class Customer {
	@Id
	private String customerId;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String email;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
	private Address address;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
	private Cart cart;
	
	
    @JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id") 
    private OrderTable orderTable;
	 
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	
	
	
	public OrderTable getOrderTable() {
		return orderTable;
	}
	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	/*
	 * public Order getOrder() { return order; } public void setOrder(Order order) {
	 * this.order = order; }
	 */
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public Address getAddress() {
//		return address;
//	}
//	public void setAddress(Address address) {
//		this.address = address;
//	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mobileNumber=" + mobileNumber + ", email=" + email + ", user=" + user + "]";
	}
	

}
