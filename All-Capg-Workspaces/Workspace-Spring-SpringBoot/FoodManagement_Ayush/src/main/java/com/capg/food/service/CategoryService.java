package com.capg.food.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.capg.food.dao.*;
import com.capg.food.entity.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO dao;

    public void addCategory(String name, String desc) {
        Category c = new Category();
        c.setCategoryName(name);
        c.setDescription(desc);
        dao.save(c);
    }

    public List<Category> getAll() {
        return dao.findAll();
    }
}