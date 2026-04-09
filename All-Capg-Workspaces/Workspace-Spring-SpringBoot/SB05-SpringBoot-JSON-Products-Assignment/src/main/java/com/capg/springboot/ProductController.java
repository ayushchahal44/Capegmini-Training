package com.capg.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping("login.spring")
	public String loginValid() {
		return "Welcome to Product Assignment";
	}
	
	@RequestMapping("/productList")
	
	public List listProduct() {
		return productService.getAllProduct();
	}
	

}