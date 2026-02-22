package com.capg.assessment.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ORDERS")   // Oracle-safe uppercase
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "order_seq_gen")

    @SequenceGenerator(
            name = "order_seq_gen",
            sequenceName = "order_seq",
            allocationSize = 1
    )
    private Long id;

    private String orderDate;
    private double totalAmount;

    // 🔹 Many Orders → One Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // 🔹 Many Orders ↔ Many Products
    @ManyToMany
    @JoinTable(
            name = "ORDER_PRODUCTS",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();


    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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
}