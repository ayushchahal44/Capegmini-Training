package com.capg.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;

public class Employee_Client {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure("hibernate_annotation.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction txn = session.beginTransaction();
		try {
			Employee employee = new Employee();
			employee.setFirstName("Ayush");
			employee.setLastName("Chahal");
			employee.setSalary(100000);
			session.save(employee);
			txn.commit();
		}catch(HibernateException e) {
			//TODO Auto-generate catch block.
			txn.rollback();
			System.out.println("exception while creating employee "+e);
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
}
