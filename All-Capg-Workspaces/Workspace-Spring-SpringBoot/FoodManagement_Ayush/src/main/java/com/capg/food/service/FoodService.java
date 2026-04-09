package com.capg.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.capg.food.dao.*;
import com.capg.food.entity.*;

@Service
public class FoodService {

    @Autowired
    private FoodItemDAO foodDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    public void addFoodItem(Long catId, String name, double price) {

        Category c = categoryDAO.findById(catId);

        FoodItem f = new FoodItem();
        f.setItemName(name);
        f.setPrice(price);
        f.setCategory(c);

        foodDAO.save(f);
    }

    public List<FoodItem> getItemsByCategory(Long id) {
        return foodDAO.findByCategory(id);
    }

    public void removeFoodItem(Long id) {
        foodDAO.delete(id);
    }
}