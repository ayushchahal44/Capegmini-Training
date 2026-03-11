package com.example.product.web;

 

import com.example.product.model.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

	//http://localhost:8081/products/101
    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        // dummy data 
        return new Product(id, "Laptop" + id, 65000.00);
    }
}
