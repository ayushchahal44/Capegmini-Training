package com.capg.springboot.controller;

import java.util.List;

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

import com.capg.springboot.Exceptions.AddressNotFoundException;
import com.capg.springboot.entity.Address;
import com.capg.springboot.service.IAddressService;

/*/*****************************************************************************
 * IAddress Controller Class
 * 
 * Created By: 
 * Date: 
 *******************************************************************************/

@RestController
@RequestMapping("/addressController")
public class IAddressController {
	
	@Autowired
	private IAddressService iaddressservice;
	
	/* Method: addAddress
	 * Description: It allows to add the address.
	 * @RequestMapping: It is used to map HTTP requests to handler methods of MVC and REST controllers.
	 * @RestController: It is used to create RESTful web services using MVC.
	 * @PostMapping: It is used to handle the HTTP POST requests matched with given URL expression.
	 * @Autowired: It enables to inject object dependency implicitly.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */
	
	@PostMapping("/addAddress")
	public ResponseEntity<Address> addAddress(@RequestBody Address add){
		iaddressservice.addAddress(add);
		return new ResponseEntity(add,HttpStatus.OK);
	}
//*********************************************************************************************************************	
	
	/* Method: removeAddress
	 * Description: It allows to remove the address.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @DeleteMapping: It is used to handle the HTTP DELETE requests matched with given URL expression.
     */
	
	@DeleteMapping("/removeAddress")
	public ResponseEntity<Address> removeAddress(@RequestBody Address add){
		iaddressservice.removeAddress(add);
		return new ResponseEntity(add,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	/* Method: updateAddress
	 * Description: It allows to update the address.
	 * @PutMapping: It is used to handle the HTTP PUT requests matched with given URL expression.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */
	
	@PutMapping("/updateAddress")
	public ResponseEntity<Address> updateAddress(@RequestBody Address add){
		iaddressservice.updateAddress(add);
		return new ResponseEntity(add,HttpStatus.OK);
	}

//*********************************************************************************************************************	

	/* Method: getAddressbyId
	 * Description: It allows to update the address.
	 * @PathVariable: It is used to handle template variables in the request URL
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
     */
	
	@GetMapping("/viewAddressBy/{id}")
	public ResponseEntity<Address> viewAddressBy(@PathVariable String id )throws AddressNotFoundException{
		Address address=iaddressservice.viewAddressBy(id);
		return new ResponseEntity(address,HttpStatus.OK);
 	}
	
//*********************************************************************************************************************
	
	/* Method: getallAddres
	 * Description: It allows to update the address.
	 * @GetMapping:It is used to handle the HTTP GET requests matched with given URL Expression.
     */
	
	@GetMapping("/viewAllAddress")
	public ResponseEntity<List<Address>> viewAllAddress() throws AddressNotFoundException {
		List<Address> address_viewall = iaddressservice.viewAllAddress();
		return new ResponseEntity(address_viewall,HttpStatus.OK);
	}
}
