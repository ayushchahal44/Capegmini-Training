package com.capg.assessment.DAO;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import java.util.List;
import com.capg.assessment.Bean.BankBean;

public class BankDAO {

 SessionFactory sf =
   new Configuration().configure().buildSessionFactory();

 public void createAccount(BankBean b) {

  Session s = sf.openSession();
  Transaction t = s.beginTransaction();

  s.save(b);

  saveTransaction(b.getAccno(),"Account Created");

  t.commit();
  s.close();
 }

 public double showBalance(int accno) {

	 Session s = sf.openSession();

	 BankBean b =
	   (BankBean) s.get(BankBean.class, accno);

	 s.close();

	 if(b == null) {
	  System.out.println("Account Not Found");
	  return 0;
	 }

	 return b.getBalance();
	}


 public void deposit(int accno,double amt) {

  Session s = sf.openSession();
  Transaction t = s.beginTransaction();

  BankBean b =
    (BankBean)s.get(BankBean.class,accno);

  b.setBalance(b.getBalance()+amt);
  s.update(b);

  saveTransaction(accno,"Deposited "+amt);

  t.commit();
  s.close();
 }

 public void withdraw(int accno,double amt) {

  Session s = sf.openSession();
  Transaction t = s.beginTransaction();

  BankBean b =
    (BankBean)s.get(BankBean.class,accno);

  if(b.getBalance()>=amt) {

   b.setBalance(b.getBalance()-amt);
   s.update(b);

   saveTransaction(accno,"Withdrawn "+amt);
  }

  t.commit();
  s.close();
 }

 public void transfer(int from,int to,double amt) {

  withdraw(from,amt);
  deposit(to,amt);

  saveTransaction(from,
   "Transferred "+amt+" to "+to);
 }

 public void printTransactions(int accno) {

  Session s = sf.openSession();

  List list = s.createSQLQuery(
   "select transaction from transactions where accno="+accno
  ).list();

  for(Object o:list)
   System.out.println(o);

  s.close();
 }

 private void saveTransaction(int accno,String msg) {

  Session s = sf.openSession();
  Transaction t = s.beginTransaction();

  s.createSQLQuery(
   "insert into transactions values("+accno+",'"+msg+"')"
  ).executeUpdate();

  t.commit();
  s.close();
 }
}
