package com.capg.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class InsertStudent {
	public static void main(String[] args) {
		//1.Start Hibernate framework
		Configuration cfg = new Configuration();
		cfg.configure(); //loads hibernate.cfg.xml
		//2.Build SessionFactory
		SessionFactory factory = cfg.buildSessionFactory();
		//3.Open Session
		Session session = factory.openSession();
		//4.Begin Transaction
		Transaction tx = session.beginTransaction();
		try {
			//5.Create Student object 1
			Student s1 = new Student();
			s1.setSno(9);
			s1.setSname("Ram");
			s1.setEmail("Ram@gmail.com");
			s1.setMobile(77777777L);
			session.save(s1);
			
			
			Student s2 = new Student();
			s2.setSno(1);
			s2.setSname("Ayush");
			s2.setEmail("ayushchahal44@gmail.com");
			s2.setMobile(9760203187L);
			session.save(s2);
			//7.Commit Transaction
			tx.commit();
			System.out.println("Records inserted successfully");
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
			factory.close();
		}
	}
}
