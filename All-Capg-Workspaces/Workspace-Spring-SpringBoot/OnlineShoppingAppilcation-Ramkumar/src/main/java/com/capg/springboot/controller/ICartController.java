package com.capg.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.capg.springboot.dto.Cart_Productdto;
import com.capg.springboot.entity.Cart;
import com.capg.springboot.entity.Product;
import com.capg.springboot.service.ICartService;

/*/*****************************************************************************
 *ICart Controller Class
 *
 * Created By:
 * Date:  
 *******************************************************************************/

@RestController
@RequestMapping("/cartController")
public class ICartController {

	@Autowired
	private ICartService icartservice;
	
	
	/* Method: addProductByCartQuantity
	 * Description: It allows to add the product to cart by quanity.
	 * @RequestMapping: It is used to map HTTP requests to handler methods of MVC and REST controllers.
	 * @RestController: It is used to create RESTful web services using MVC.
	 * @PostMapping: It is used to handle the HTTP POST requests matched with given URL expression.
	 * @Autowired: It enables to inject object dependency implicitly.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @PathVariable: It is used to handle template variables in the request URL
     */
	
	@PostMapping("/addProductToCart/{quantity}")
	public ResponseEntity<Cart> addProductToCart(@RequestBody Cart_Productdto cart_productdto,@PathVariable int quantity  ){
		Cart cart=new Cart();
		cart.setCartId(cart_productdto.getCartdto().getCartId());
		Product product=new Product();
		product.setProductId(cart_productdto.getProductdto().getProductId());
		product.setProductName(cart_productdto.getProductdto().getProductName());
		icartservice.addProductToCart(cart, product, quantity);
		return new ResponseEntity(cart,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	/* Method: updateProductQuantity
	 * Description: It allows to update the product.
	 * @PutMapping: It is used to handle the HTTP PUT requests matched with given URL expression.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @PathVariable: It is used to handle template variables in the request URL
     */
	
	@PutMapping("/updateProductQuantity/{quantity}")
	public ResponseEntity<Cart> updateProductQuantity(@RequestBody Cart_Productdto cart_productdto,@PathVariable int quantity ){
		Cart cart=new Cart();
		cart.setCartId(cart_productdto.getCartdto().getCartId());
		Product product=new Product();
		product.setProductId(cart_productdto.getProductdto().getProductId());
		product.setProductName(cart_productdto.getProductdto().getProductName());
		icartservice.addProductToCart(cart, product, quantity);
		return new ResponseEntity(cart,HttpStatus.OK);
	}
	
//*********************************************************************************************************************	
	
	/* Method: getAddressbyId
	 * Description: It allows to update the address.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
     */
	
	@GetMapping("/viewAllProducts")
	public ResponseEntity<Cart> viewAllProducts(@RequestBody Cart cart){
		icartservice.viewAllProducts(cart);
		return new ResponseEntity(cart,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	/* Method: removeAddress
	 * Description: It allows to remove the address.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @DeleteMapping: It is used to handle the HTTP DELETE requests matched with given URL expression.
     */
	
	@DeleteMapping("/removeAllProducts")
	public ResponseEntity<Cart> removeAllProducts(@RequestBody Cart cart){
		icartservice.removeAllProducts(cart);
	   return new ResponseEntity(cart,HttpStatus.OK);
	}

//*********************************************************************************************************************	

	/* Method: removeAddress
	 * Description: It allows to remove the address.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @DeleteMapping: It is used to handle the HTTP DELETE requests matched with given URL expression.
     */
	
	@DeleteMapping("/removeProductFromCart")
	public ResponseEntity<Cart> removeProductFromCart(@RequestBody Cart cart,Product p){
		icartservice.removeProductFromCart(cart,p);
		return new ResponseEntity(cart,HttpStatus.OK);
	}
	
	
}
