package com.springcore;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class ProductClient {
	//Step-1
	public static void main(String[] args) {
		//step-2
		ClassPathXmlApplicationContext factory = new ClassPathXmlApplicationContext("applicationContext.xml");
		//step-3
		ProductService p1 = (ProductService) factory.getBean("productService1");
		//step-9
		p1.allProducts();
		ProductService p2 = (ProductService) factory.getBean("productService2");
		
		p2.allProducts();
	}
}
