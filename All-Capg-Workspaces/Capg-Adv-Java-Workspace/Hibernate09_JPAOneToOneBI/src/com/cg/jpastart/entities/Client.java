package com.cg.jpastart.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 


public class Client {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-PU");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        // Create Student object
        Student1 student1 = new Student1(); 
        student1.setName("Deepak Patil");

        // Create Address object
        Address1 homeAddress = new Address1();
        homeAddress.setStreet("MG Road");
        homeAddress.setCity("Pune");
        homeAddress.setState("Maharashtra");
        homeAddress.setZipCode("411017");

        // Establish bi-directional relationship
        student1.setAddress(homeAddress);
        homeAddress.setStudent(student1);
      
       
       em.persist(homeAddress); 
        // Fetch Data
        System.out.println("\n=== DATA FROM DATABASE ===");
        System.out.println("Student ID: " + student1.getStudentId());
        System.out.println("Student Name: " + student1.getName());
        System.out.println("Student Address - Street: " + student1.getAddress().getStreet());
        System.out.println("Student Address - City: " + student1.getAddress().getCity());

        System.out.println("\nReverse Lookup from Address:");
        System.out.println("Address belongs to Student ID: " + homeAddress.getStudent().getStudentId());
        System.out.println("Address belongs to Student Name: " + homeAddress.getStudent().getName());

        System.out.println("Address belongs to Student Name: " + homeAddress.getState());

        em.getTransaction().commit();
        em.close();
        factory.close();

        System.out.println("\n Added student with address successfully!");
    }
}
