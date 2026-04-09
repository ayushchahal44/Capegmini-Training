package com.capg.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.capg.springboot.model.Product;

/**
 * REST Controller for Product Service
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    /**
     * Returns product details by product id
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        // Dummy data (Normally from DB)
        return new Product(id, "Laptop", 65000);
    }
}