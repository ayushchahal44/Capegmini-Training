package com.jpa.client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jpa.beans.Employee;

public class EmployeeClient {
	public static void main(String[] args) {
		 
		/* Create EntityManagerFactory */
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("JPACRUD");
 
		/* Create  Entity */
		Employee employee = new Employee();
		   
		   employee.setFirstname("Ramesh");
		   employee.setLastname("Kumar");
		   employee.setEmail("Ramesh@gmail.com");
		   employee.setEmpId(19);
 
		/* Create EntityManager */
		EntityManager em = emf.createEntityManager();
 
		/* Persist entity   Record Inserted*/
		em.getTransaction().begin();
		    em.persist(employee);     //Record inserted
		    
		    em.getTransaction().commit();
 
		/* Retrieve entity */
		employee = em.find(Employee.class, 19);  // find record 
		System.out.println(employee);
 
		/* Update entity */
	//	em.getTransaction().begin();
	//	employee.setFirstname("Babu");
	//	System.out.println("Update Employee Name is  :- " + employee);
	//	em.getTransaction().commit();
 
		/* Remove entity */
		em.getTransaction().begin();
		//em.remove(employee);                 // remove record
	   em.getTransaction().commit();
 
		/* Check whether enittiy is removed or not */
		employee = em.find(Employee.class, 19);
		System.out.println("Employee after removal :- " + employee);
 
	}
}

