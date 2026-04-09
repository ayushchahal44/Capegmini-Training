package com.capg.springboot.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capg.springboot.entity.Customer;
import com.capg.springboot.entity.Product;
import com.capg.springboot.entity.Address;

/*/*****************************************************************************
 *ICustomer repository Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/


public interface ICustomerRepository extends JpaRepository<Customer,String> {
	Customer findByCustomerId(String customerId);
	
	@Query("select c from Customer c where c.address= :n")
	 public List<Customer> findCustomerByCity(@Param("n") String location);
}
 