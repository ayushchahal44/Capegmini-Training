package com.ram.corejava.layers;

public class BookService {
	String grade;
	                  
	public int addBook(int bookId,String title,double price) throws ClassNotFoundException {
		System.out.println("This is BookService");
		System.out.println(bookId+title+price);
			
		if(price<=300) {
			grade="C";
			
		}
		else if(price>=300 && price<=600) {
			grade="B";
		}
		else {
			grade="A";
		}
		
		BookBean bookBean = new BookBean();
		
		  bookBean.setBookId(bookId);  
		  bookBean.setTitle(title);    
		  bookBean.setPrice(price);    
		  bookBean.setGrade(grade);    
		
		
		  BookDAO bookDAO=new BookDAO();
		  int k=bookDAO.addBook(bookBean);
		
		return k;
	}

}
