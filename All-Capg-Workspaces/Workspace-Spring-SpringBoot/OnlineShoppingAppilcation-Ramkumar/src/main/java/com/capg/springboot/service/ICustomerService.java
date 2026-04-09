package com.capg.springboot.service;

import java.util.List;
import java.util.Optional;

import com.capg.springboot.Exceptions.CustomerNotFoundException;
import com.capg.springboot.dto.Customerdto;
import com.capg.springboot.entity.Customer;

/*/*****************************************************************************
 *ICustomer Service Class 
 *
 * Created By: 
 * Date:  
 *******************************************************************************/

public interface ICustomerService {

	
	public Customer findByCustomerId(Customer cust) throws CustomerNotFoundException;
	
	public Customer removeCustomer(Customer cust);
	public List<Customer> viewAllCustomer() throws CustomerNotFoundException;

	public Customer addCustomer(Customerdto custdto);
	public List<Customer> findCustomerByCity(String location) throws CustomerNotFoundException;
}
