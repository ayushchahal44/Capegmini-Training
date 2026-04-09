package com.capg.springboot.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.Exceptions.CustomerNotFoundException;
import com.capg.springboot.dto.Customerdto;
import com.capg.springboot.entity.Customer;
import com.capg.springboot.service.ICustomerService;

/*/*****************************************************************************
 *ICart Controller Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/

@RequestMapping("/customerController")
@RestController
public class ICustomerController {
  
	@Autowired
	private ICustomerService icustomerservice;
	
	/* Method: addProductByCartQuantity
	 * Description: It allows to add the product to cart by quanity.
	 * @RequestMapping: It is used to map HTTP requests to handler methods of MVC and REST controllers.
	 * @RestController: It is used to create RESTful web services using MVC.
	 * @PostMapping: It is used to handle the HTTP POST requests matched with given URL expression.
	 * @Autowired: It enables to inject object dependency implicitly.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @PathVariable: It is used to handle template variables in the request URL
     */
	

	@PostMapping("/addCust")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customerdto customer){
		Customer custImpl=icustomerservice.addCustomer(customer);
		return new ResponseEntity(custImpl,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	/* Method: viewCustomerByLocation
	 * Description: It allows to view Customer by Location.
	 * @PathVariable: It is used to handle template variables in the request URL
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
     */
	
	@GetMapping("/viewCust/{location}")
	public ResponseEntity<List<Customer>> ViewAllCustomers(@PathVariable String location) throws CustomerNotFoundException{
		List<Customer> custImpl=icustomerservice.findCustomerByCity(location);
		return new ResponseEntity(custImpl,HttpStatus.OK);
		}

//*********************************************************************************************************************	

	/* Method: viewCustomer
	 * Description: It allows to view the Customer.
	 * @PathVariable: It is used to handle template variables in the request URL
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */	
	//http://localhost:9090/customerController/viewAllCustomer
	
	@GetMapping("/viewAllCustomer") 
	public ResponseEntity<List<Customer>> viewAllCustomer() throws CustomerNotFoundException
	{ 
	List<Customer> custImpl=icustomerservice.viewAllCustomer();
	return new  ResponseEntity(custImpl,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	/* Method: removeCustomer
	 * Description: It allows to remove the Customer.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @DeleteMapping: It is used to handle the HTTP DELETE requests matched with given URL expression.
     */
	
	@DeleteMapping("/removeCust") 
	public ResponseEntity<Customer> removeCustomer(@RequestBody Customer customer){
       Customer custImpl=icustomerservice.removeCustomer(customer);
       return new ResponseEntity(custImpl,HttpStatus.OK); 
       }

//*********************************************************************************************************************	
	
		/* Method: viewAllCustomer
		 * Description: It allows to view All the Customers.
		 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
		 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
	     */
		
	@GetMapping("/viewCust") 
	public ResponseEntity<Customer> findByCustomerId(@RequestBody Customer customer)throws CustomerNotFoundException
	{ 
	Customer custImpl=icustomerservice.findByCustomerId(customer);
	return new  ResponseEntity(custImpl,HttpStatus.OK);
	}
}


