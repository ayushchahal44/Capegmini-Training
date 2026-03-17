package com.capg.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.springboot.Exceptions.CustomerNotFoundException;
import com.capg.springboot.dto.Customerdto;
import com.capg.springboot.entity.Address;
import com.capg.springboot.entity.Cart;
import com.capg.springboot.entity.Customer;
import com.capg.springboot.repository.IAddressRepository;
import com.capg.springboot.repository.ICartRepository;
import com.capg.springboot.repository.ICustomerRepository;

/*/*****************************************************************************
 *ICustomer ServiceImpl Class 
 *
 * Created By:
 * Date:
 *******************************************************************************/

@Service
@Transactional
public class ICustomerServiceImpl implements ICustomerService {
	@Autowired
	private ICustomerRepository icustomerrepository;
	@Autowired
	private IAddressRepository add_repo;
	@Autowired
	private ICartRepository cart_repo;
	
	
	
	

	/* Method: addCustomer 
	 * Description: It adds customer to database
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the customer. 
     */
	
	@Override
	public Customer addCustomer(Customerdto custdto) {
		Address address=add_repo.findByAddressId(custdto.getAddressid());
		Cart cart=cart_repo.findByCartId(custdto.getCartId());
		Customer customer=new Customer();
		customer.setAddress(address);
		customer.setCart(cart);
		customer.setCustomerId(custdto.getCustomerId());
		customer.setEmail(custdto.getEmail());
		customer.setFirstName(custdto.getFirstName());
		customer.setLastName(custdto.getLastName());
		customer.setMobileNumber(custdto.getMobileNumber());
		return icustomerrepository.save(customer);
	}
	

//*********************************************************************************************************************	
	
		/* Method: removeCustomer 
		 * Description: It remove customer from the database.
		 * @Override: It indicates that the child class method is over-writing its base class method.
	     * return: It returns the customer. 
	     */
	
	@Override
	public Customer removeCustomer(Customer cust) {
		icustomerrepository.delete(cust);
        return cust;
	}
	
//*********************************************************************************************************************	
	
		/* Method: viewAllCustomer 
		 * Description: It find the customer by id
		 * @Override: It indicates that the child class method is over-writing its base class method.
	     * return: It returns the customer. 
	     */

	@Override
	public List<Customer> viewAllCustomer() throws CustomerNotFoundException
	{
		// TODO Auto-generated method stub
		 
	   List<Customer> customer=icustomerrepository.findAll();
		
		  if(customer==null) throw new CustomerNotFoundException("CustomerNOtFound");
		 
	     return customer;     
	}

//*********************************************************************************************************************	
	
	/* Method: findingCustomerbyCity
	* Description: It find the customer by city
	* @Override: It indicates that the child class method is over-writing its base class method.
	* return: It returns the customer. 
	*/

	@Override
	public List<Customer> findCustomerByCity(String location)throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		
		
		List<Customer> customer= icustomerrepository.findCustomerByCity(location);
		   if(customer==null)
			   throw new CustomerNotFoundException("Customer not Found");
		return customer;
	}

//*********************************************************************************************************************	
	
		/* Method: findingCustomerbyid
		* Description: It find the customer by id
		* @Override: It indicates that the child class method is over-writing its base class method.
		* return: It returns the customer. 
		*/
	@Override
	public Customer findByCustomerId(Customer cust) throws CustomerNotFoundException{
		// TODO Auto-generated method stub
		 
	   Customer customer=icustomerrepository.findByCustomerId(cust.getCustomerId());
	          if(customer==null)
	        	  throw new CustomerNotFoundException("CustomerNOtFound");
	     return customer;     
	}

}
