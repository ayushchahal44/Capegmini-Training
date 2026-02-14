package com.capg.assessment.Service;

import com.capg.assessment.Bean.BankBean;
import com.capg.assessment.DAO.BankDAO;

public class BankService {

 BankDAO dao = new BankDAO();

 public void createAccount(BankBean b){
  dao.createAccount(b);
 }

 public double showBalance(int accno){
  return dao.showBalance(accno);
 }

 public void deposit(int accno,double amt){
  dao.deposit(accno,amt);
 }

 public void withdraw(int accno,double amt){
  dao.withdraw(accno,amt);
 }

 public void transfer(int from,int to,double amt){
  dao.transfer(from,to,amt);
 }

 public void printTransactions(int accno){
  dao.printTransactions(accno);
 }
}
