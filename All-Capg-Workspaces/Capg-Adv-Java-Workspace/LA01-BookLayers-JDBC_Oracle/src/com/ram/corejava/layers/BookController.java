package com.ram.corejava.layers;

import java.util.Scanner;
// create table book(bookid int,title varchar2(20),price float,grade varchar2(20));
public class BookController {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter BookID: ");
		int bookId = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Book Title: ");
		String title=sc.nextLine();
		System.out.println("Enter Book price: ");
		double price=sc.nextDouble();
		BookService bookService=new BookService();
		                        
		int t=bookService.addBook(bookId,title,price);
		
		System.out.println("BookController return value for db  :"+t);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}
