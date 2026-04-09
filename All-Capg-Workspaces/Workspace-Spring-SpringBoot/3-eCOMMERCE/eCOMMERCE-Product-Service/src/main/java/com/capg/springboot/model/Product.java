package com.capg.springboot.model;


/**
 * Product Model (POJO)
 * Represents product details
 */
public class Product {

    private Long id;
    private String name;
    private double price;

    // Constructor
    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}