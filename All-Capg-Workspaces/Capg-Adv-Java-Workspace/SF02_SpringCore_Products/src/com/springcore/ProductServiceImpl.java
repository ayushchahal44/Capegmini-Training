package com.springcore;

public class ProductServiceImpl implements ProductService{
	String product; //Step-8 MacAirM12021 // Step-16 MacProM12021
	public ProductServiceImpl() {
	} //Step-6
	//One parameter Constructor
		public ProductServiceImpl(String product) {
			this.product = product;
		}
		
		public void setProduct(String product) {
			this.product = product;
		}
		
		public void allProducts() {
			System.out.println("Product name is " + product);
		}
	}
