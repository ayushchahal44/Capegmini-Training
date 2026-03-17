package com.capg.springboot.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.springboot.Exceptions.OrderNotFoundException;
import com.capg.springboot.dto.Orderdto;
import com.capg.springboot.entity.Address;
import com.capg.springboot.entity.Customer;
import com.capg.springboot.entity.OrderTable;
import com.capg.springboot.repository.IAddressRepository;
import com.capg.springboot.repository.ICustomerRepository;
import com.capg.springboot.repository.IOrderRepository;

/*/*****************************************************************************
 *IOrder ServiceImpl Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/

@Service
@Transactional
public class IOrderServiceImpl implements IOrderService {

	
	@Autowired
	private IOrderRepository iorderrepository;
	@Autowired
	private IAddressRepository iaddressrepository;
	@Autowired
	private ICustomerRepository icustomerrepository;
	
	
 //*********************************************************************************************************************	
	
  	/* Method: viewAllorder
  	 * Description: It view the order of custmer 
  	 * @Override: It indicates that the child class method is over-writing its base class method.
  	 * return: It returns the order. 
  	 */
    
	@Override
	public List<OrderTable> getorder() 
	{
		List<OrderTable> allorder = iorderrepository.findAll();
		return allorder;
	}
	
//*********************************************************************************************************************	
	
	/* Method: addOrder 
	 * Description: It adds the order for customer
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the order. 
     */

	@Override
	public OrderTable addOrder(Orderdto orderdto) {
		Address address=iaddressrepository.findByAddressId(orderdto.getAddressId());
		Customer cust=icustomerrepository.findByCustomerId(orderdto.getCustomerId());
		OrderTable order=new OrderTable();
		order.setAddress(address);
		order.setCustomer(cust);
		order.setOrderId(orderdto.getOrderId());
		order.setOrderDate(orderdto.getOrderDate());
		order.setOrderStatus(orderdto.getOrderStatus());
		return iorderrepository.save(order);
			}
	
//*********************************************************************************************************************	
	
		/* Method: updateOrder 
		 * Description: It updates the order for customer
		 * @Override: It indicates that the child class method is over-writing its base class method.
	     * return: It returns the order. 
	     */

	
	@Override
	public OrderTable updateOrder(Orderdto orderdto) {
		Address address=iaddressrepository.findByAddressId(orderdto.getAddressId());
		Customer cust=icustomerrepository.findByCustomerId(orderdto.getCustomerId());
		OrderTable order=new OrderTable();
		order.setAddress(address);
		order.setCustomer(cust);
		order.setOrderId(orderdto.getOrderId());
		order.setOrderDate(orderdto.getOrderDate());
		order.setOrderStatus(orderdto.getOrderStatus());
		return iorderrepository.save(order);
	}
	
//*********************************************************************************************************************	
	
	/* Method: removeorder 
	 * Description: It remove the order for customer
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the order. 
     */
	
	@Override
	public OrderTable removeOrder(OrderTable order) {
		iorderrepository.delete(order);
	       return order;
	}
	
//*********************************************************************************************************************	
	
	/* Method: vieworder
	 * Description: It views order of customer
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the order. 
     */
	
	@Override
	public OrderTable viewOrder(OrderTable order) {
		// TODO Auto-generated method stub
		iorderrepository.findAll();
		return null;
	}
	
//*********************************************************************************************************************	
	
		/* Method: viewAllorder
		 * Description: It views all order of customer
		 * @Override: It indicates that the child class method is over-writing its base class method.
	     * return: It returns the order. 
	     */
	
	@Override
	public List<OrderTable> viewAllOrders(LocalDate date) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		List<OrderTable> order=iorderrepository.findOrderByDate(date);
		    if(order==null)
		    	throw new OrderNotFoundException("Order not found");
		return order;
	}
	
//*********************************************************************************************************************	
	
	/* Method: findCustomerBycity
	 * Description: It find the customer bu sity
	 * @Override: It indicates that the child class method is over-writing its base class method.
	 * return: It returns the order. 
	 */
	
	
    @Override
	public List<OrderTable> findCustomerByCity(String location) throws OrderNotFoundException {
		// TODO Auto-generated method stub
	      List<OrderTable> order=iorderrepository.findOrderByCity(location);
	            if(order==null)
	                 throw new  OrderNotFoundException("Order not found");
	      return order;
		
	}



}
