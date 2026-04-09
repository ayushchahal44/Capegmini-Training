package com.capg.assessment.Controller;

import java.util.Scanner;
import com.capg.assessment.Bean.BankBean;
import com.capg.assessment.Service.BankService;

public class BankController {

 public static void main(String[] args) {

  Scanner sc = new Scanner(System.in);
  BankService service = new BankService();

  while(true) {

   System.out.println("\n===== BANK MENU =====");
   System.out.println("1. Create a New Account");
   System.out.println("2. Show Account Balance");
   System.out.println("3. Deposit Money");
   System.out.println("4. Withdraw Money");
   System.out.println("5. Fund Transfer");
   System.out.println("6. Print Transactions");
   System.out.println("0. Exit");
   System.out.print("Enter your choice: ");

   int ch = sc.nextInt();

   switch(ch) {

    case 1:

     BankBean b = new BankBean();

     System.out.print("Account No: ");
     b.setAccno(sc.nextInt());

     System.out.print("Name: ");
     b.setName(sc.next());

     System.out.print("Mobile: ");
     b.setMobile(sc.nextLong());

     System.out.print("Balance: ");
     b.setBalance(sc.nextDouble());

     service.createAccount(b);
     System.out.println("Account Created Successfully");
     break;

    case 2:

     System.out.print("Account No: ");
     System.out.println(
       "Balance: " +
       service.showBalance(sc.nextInt()));
     break;

    case 3:

     System.out.print("Account No: ");
     int dacc = sc.nextInt();

     System.out.print("Amount: ");
     double damt = sc.nextDouble();

     service.deposit(dacc, damt);
     System.out.println("Amount Deposited");
     break;

    case 4:

     System.out.print("Account No: ");
     int wacc = sc.nextInt();

     System.out.print("Amount: ");
     double wamt = sc.nextDouble();

     service.withdraw(wacc, wamt);
     System.out.println("Amount Withdrawn");
     break;

    case 5:

     System.out.print("From AccNo: ");
     int from = sc.nextInt();

     System.out.print("To AccNo: ");
     int to = sc.nextInt();

     System.out.print("Amount: ");
     double tamt = sc.nextDouble();

     service.transfer(from, to, tamt);
     System.out.println("Transfer Successful");
     break;

    case 6:

     System.out.print("Account No: ");
     service.printTransactions(sc.nextInt());
     break;

    case 0:

     System.out.println("Exiting... Thank You!");
     System.exit(0);   // terminate program

    default:

     System.out.println("Invalid Choice");
   }
  }
 }
}
