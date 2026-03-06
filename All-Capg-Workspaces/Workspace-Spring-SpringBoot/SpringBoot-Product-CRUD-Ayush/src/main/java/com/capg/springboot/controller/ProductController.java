
package com.capg.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.entities.Product;
import com.capg.springboot.service.ProductServiceI;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceI service;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product = service.createProduct(product);
        return new ResponseEntity<Product>(product, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        Product product = service.findProductById(id);
        return new ResponseEntity<Product>(product, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = service.findAllProducts();
        return new ResponseEntity<List<Product>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        product = service.updateProduct(product);
        return new ResponseEntity<Product>(product, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        service.deleteProduct(id);
        return "Product Deleted";
    }
}
