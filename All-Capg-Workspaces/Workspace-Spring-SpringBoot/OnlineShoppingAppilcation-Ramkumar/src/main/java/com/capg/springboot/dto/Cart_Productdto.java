package com.capg.springboot.dto;
//Used for data transfer
public class Cart_Productdto {
	private Cartdto cartdto;
	private Productdto productdto;
	public Cartdto getCartdto() {
		return cartdto;
	}
	public void setCartdto(Cartdto cartdto) {
		this.cartdto = cartdto;
	}
	public Productdto getProductdto() {
		return productdto;
	}
	public void setProductdto(Productdto productdto) {
		this.productdto = productdto;
	}
	
}
