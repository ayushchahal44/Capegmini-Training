package com.capg.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product")
public class Product {
	@Id
	private String productId;
	private String productName;
	private double price;
	private String color;
	private String dimension;
	private String specification;
	private String manufacturer;
	private int quantity;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cat_id")
	private Category category;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
	 private Cart cart;
	
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private OrderTable orderTable;
	
		/*
		 * @JsonIgnore
		 * 
		 * @OneToOne(fetch = FetchType.LAZY)
		 * 
		 * @JoinColumn(name="product_id") private Order order;
		 * 
		 * public Order getOrder() { return order; } public void setOrder(Order order) {
		 * this.order = order; }
		 */
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public OrderTable getOrderTable() {
		return orderTable;
	}
	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	
		}
	
