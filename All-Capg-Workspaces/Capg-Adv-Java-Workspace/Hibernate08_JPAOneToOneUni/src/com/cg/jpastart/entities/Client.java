package com.cg.jpastart.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Client {

	public static void main(String[] args) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-PU");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
	
		Student student = new Student();
			
		student.setName("Ramesh");
		
		Address homeAddress = new Address();
		
		homeAddress.setStreet("MG Road1");
		homeAddress.setCity("Pune1");
		homeAddress.setState("Maharashtra1");
		homeAddress.setZipCode("411 0171");
		//inject address into student
		student.setAddress(homeAddress);
		
		em.persist(student);
		//em.persist(homeAddress);
		em.flush();   // forces INSERT immediately
		em.getTransaction().commit();
		
		System.out.println("Added one student with address to database.");
		
		
				
				
				System.out.println(homeAddress.getCity());
								
				System.out.println(" student  State  address : "+student.getAddress().getState());
	
				System.out.println(" student  City  address : "+student.getAddress().getCity());
				System.out.println(" student  Street  address : "+student.getAddress().getStreet());
				System.out.println(" student  ZipCode  address : "+student.getAddress().getZipCode());
		em.close();
		factory.close();
	}
}
