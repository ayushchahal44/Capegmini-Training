package com.springcore;

public class ProductClient {
                     // 1. 
	public static void main(String[] args) 
	{
		                         // Constructor 2.
		ProductServiceImpl p1=new ProductServiceImpl("MacAirM12021"); 		
		p1.allProducts();		
		
		                       // Setter calling
		ProductServiceImpl p2=new ProductServiceImpl();
		
		  p2.setProduct("MacProM12021");
		  
		  p2.allProducts();

	}

}
