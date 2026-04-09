package com.capg.springboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.Exceptions.ProductNotFoundException;
import com.capg.springboot.dto.Productdto;
import com.capg.springboot.entity.Product;
import com.capg.springboot.service.IProductService;

/*/*****************************************************************************
 * IProduct Controller Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/


@RestController
@RequestMapping("/productController")
public class IProductController {
	
	@Autowired
	private IProductService iproductservice;
	
	/* Method: addProduct
	 * Description: It allows to add the Product.
	 * @RequestMapping: It is used to map HTTP requests to handler methods of MVC and REST controllers.
	 * @RestController: It is used to create RESTful web services using MVC.
	 * @PostMapping: It is used to handle the HTTP POST requests matched with given URL expression.
	 * @Autowired: It enables to inject object dependency implicitly.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */
	
	@PostMapping("/addProduct")
	public ResponseEntity<Product> addProduct(@RequestBody Productdto product){
		Product productimpl=iproductservice.addProduct(product);
		return new ResponseEntity(productimpl,HttpStatus.OK);
	}
	
//*********************************************************************************************************************	
	
	/* Method: getallproduct
	 * Description: It allows to update the address.
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
     */
		
	@GetMapping("/viewAllProducts")
	public ResponseEntity<Product> viewAllProducts(){
		List<Product> productImpl=iproductservice.viewAllProducts();
		return new ResponseEntity(productImpl,HttpStatus.OK);
	}
	

//*********************************************************************************************************************	
	
	/* Method: updateProduct
	 * Description: It allows to add the address.
	 * @PostMapping: It is used to handle the HTTP POST requests matched with given URL expression.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */
	
	@PostMapping("/updateProduct")
	public ResponseEntity<Product> updateProduct(@RequestBody Productdto product){
		iproductservice.updateProduct(product);
		return new ResponseEntity(product,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	/* Method: removeProduct
	 * Description: It allows to remove the address.
	 * @DeleteMapping: It is used to handle the HTTP DELETE requests matched with given URL expression.
	 * @PathVariable: It is used to handle template variables in the request URL
     */

	@DeleteMapping("/removeProduct")
	public ResponseEntity<Product> removeProduct(@RequestBody Product product) throws ProductNotFoundException{
		iproductservice.removeProduct(product);
		return new ResponseEntity(product,HttpStatus.OK);
	}

//*********************************************************************************************************************	

	/* Method: viewproductbycname
	 * Description: It allows to get the product by cname.
	 * @PathVariable: It is used to handle template variables in the request URL
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
     */
	
	@GetMapping("viewProduct/{cname}")
	public ResponseEntity<List<Product>>  findAllProductByCategory(@PathVariable String cname) throws ProductNotFoundException {
		List<Product> product=iproductservice.findAllProductByCategory(cname);
		return new ResponseEntity(product,HttpStatus.OK);
	}
	
}
