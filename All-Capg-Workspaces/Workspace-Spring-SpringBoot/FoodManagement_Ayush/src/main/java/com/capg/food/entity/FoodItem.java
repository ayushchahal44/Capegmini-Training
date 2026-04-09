package com.capg.food.entity;

import javax.persistence.*;

@Entity
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq")
	@SequenceGenerator(name = "food_seq", sequenceName = "food_seq", allocationSize = 1)
	private Long id;

    private String itemName;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}