package com.capg.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.springboot.dto.Orderdto;
import com.capg.springboot.entity.Address;
import com.capg.springboot.entity.OrderTable;
import com.capg.springboot.service.IOrderService;


/*/*****************************************************************************
 *IOrder Controller Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/

@RestController
@RequestMapping("/orderController")
public class IOrderController {

	@Autowired
	private IOrderService iorderservice;


	/* Method: addOrder
	 * Description: It allows to add the Order.
	 * @RequestMapping: It is used to map HTTP requests to handler methods of MVC and REST controllers.
	 * @RestController: It is used to create RESTful web services using MVC.
	 * @PostMapping: It is used to handle the HTTP POST requests matched with given URL expression.
	 * @Autowired: It enables to inject object dependency implicitly.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */
	
	@PostMapping("/addOrder")
	public ResponseEntity<OrderTable> addOrder(@RequestBody Orderdto orderdto){
      OrderTable order=iorderservice.addOrder(orderdto);
      return new ResponseEntity(order,HttpStatus.OK);
      
	}

//*********************************************************************************************************************	

	/* Method: updateOrder
	 * Description: It allows to update the order.
	 * @PutMapping: It is used to handle the HTTP PUT requests matched with given URL expression.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
     */

	
	
	@PutMapping("/updateOrder")
	public ResponseEntity<OrderTable> updateOrder(@RequestBody Orderdto orderdto){
		 OrderTable order=iorderservice.updateOrder(orderdto);
	      return new ResponseEntity(order,HttpStatus.OK);
	}

//*********************************************************************************************************************	
	
	
	@GetMapping("/viewAllOrder")
	public List<OrderTable> viewAllOrder()
	{
		List<OrderTable> tb = iorderservice.getorder();
		return tb;
	}
	
	
//*********************************************************************************************************************	
	
	/* Method: removeOrder
	 * Description: It allows to remove the Order.
	 * @RequestBody: It is used to bind HTTP request body with a domain object in method parameter or return type
	 * @DeleteMapping: It is used to handle the HTTP DELETE requests matched with given URL expression.
     */
	
	@DeleteMapping("/removeOrder")
	public ResponseEntity<OrderTable> removeOrder(@RequestBody OrderTable ordertable){
		iorderservice.removeOrder(ordertable);
		return new ResponseEntity(ordertable,HttpStatus.OK);

}
	
}
