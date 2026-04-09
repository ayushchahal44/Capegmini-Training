package com.capg.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    private Long orderId;
    public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	private String product;
    private Double price;

    public OrderEntity() {}
    public OrderEntity(Long orderId, String product, Double price) {
        this.orderId = orderId;
        this.product = product;
        this.price = price;
    }

    // getters & setters
}