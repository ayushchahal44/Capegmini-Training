package com.capg.springboot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capg.springboot.entity.OrderTable;
import com.capg.springboot.entity.Product;

/*/*****************************************************************************
 *IOrder Repository Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/

public interface IOrderRepository extends JpaRepository<OrderTable,String> {
	
	@Query("select p from OrderTable p where p.address=:c")
	 public List<OrderTable> findOrderByCity(@Param("c") String location);
	
	@Query("select p from OrderTable p where p.address=:c")
	 public List<OrderTable> findOrderByDate(@Param("c") LocalDate date);
}