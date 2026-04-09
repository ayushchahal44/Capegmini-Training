package com.capg.springboot;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	public List productList = Arrays.asList(
			new ProductBean("01","laptop",12000),
			new ProductBean("02","Mobile",15000),
			new ProductBean("03","earbud",16000));
	
	public List<ProductBean> getAllProduct(){
		return productList;
	}

}