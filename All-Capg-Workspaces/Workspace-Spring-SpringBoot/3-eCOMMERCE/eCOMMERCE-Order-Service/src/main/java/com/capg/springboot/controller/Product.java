package com.capg.springboot.controller;



/**
 * Product DTO used in Order Service
 */
public class Product {

    private Long id;
    private String name;
    private double price;

    // Getters & Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}