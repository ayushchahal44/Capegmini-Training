package com.capg.springboot.entity;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*/*****************************************************************************
 * IAddress Controller Class
 * 
 * Created By: Siddharth Shitole
 * Date:  
 *******************************************************************************/

@Entity
@Table(name="address")
public class Address {
	@Id
	private String addressId;
	private String streetNo;
	private String buildingName;
	private String  city;
	private String state;
	private String country;	
	private String pincode;
	
	@OneToOne(mappedBy = "address",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Customer customer;
     
	/*
	 * @JsonIgnore
	 * 
	 * @OneToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name="order_id") private Order order;
	 */
	
	/*
	 * public Order getOrder() { return order; } public void setOrder(Order order) {
	 * this.order = order; }
	 */
	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private OrderTable orderTable;
	
	
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Address(String addressId, String streetNo, String buildingName, String city, String state, String country,
			String pincode, Customer customer, OrderTable orderTable) {
		super();
		this.addressId = addressId;
		this.streetNo = streetNo;
		this.buildingName = buildingName;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.customer = customer;
		this.orderTable = orderTable;
	}
	public OrderTable getOrderTable() {
		return orderTable;
	}
	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getStreetNo() {
		return streetNo;
	}
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
}
