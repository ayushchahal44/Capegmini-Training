package com.springcore;

public class ProductServiceImpl implements ProductService {
       String product;  //MacAirM12021  MacProM12021
       
       public ProductServiceImpl() {
    	   
       }
       // One paramter Constructor  MacAirM12021  3.
public ProductServiceImpl(String product) { 
	   this.product=product;
    	   
       }
             // Setter Method  MacProM12021
  public void setProduct(String product) {
	  this.product=product; //MacProM12021
  }
            // method
	public void allProducts() {
		System.out.println("Product Name is  :"+product);
	}
}
