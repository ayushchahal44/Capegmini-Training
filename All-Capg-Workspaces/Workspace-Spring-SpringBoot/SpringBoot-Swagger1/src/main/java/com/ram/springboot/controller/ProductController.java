package com.ram.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	//http://localhost:9092/api/products/10
    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable int id) {
        return ResponseEntity.ok("Product ID: " + id);
    }
    //http://localhost:9092/api/products/'ramesh'
    @PostMapping("/{name}")
    public ResponseEntity<String> createProduct(@RequestBody String name) {
        return ResponseEntity.ok("Product Name : " + name);
    }
}
