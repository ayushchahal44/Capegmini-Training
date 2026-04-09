

package com.ram.hibernate.service;

import com.ram.hibernate.dao.BookDAO;
import com.ram.hibernate.entity.BookBean;

public class BookService {

    BookDAO dao = new BookDAO();

    /* CREATE */
    public void addBook(int id, String title, double price) {

        String grade;
        if (price <= 300)
            grade = "C";
        else if (price <= 600)
            grade = "B";
        else
            grade = "A";

        BookBean book = new BookBean();
        book.setBookId(id);
        book.setTitle(title);
        book.setPrice(price);
        book.setGrade(grade);

        dao.addBook(book);
        System.out.println("Book Inserted");
    }

    /* READ */
    public void getBook(int id) {
        BookBean book = dao.getBook(id);
        if (book != null) {
            System.out.println(
                book.getBookId() + " " +
                book.getTitle() + " " +
                book.getPrice() + " " +
                book.getGrade()
            );
        } else {
            System.out.println("Book Not Found");
        }
    }

    /* UPDATE */
    public void updateBookPrice(int id, double newPrice) {

        BookBean book = dao.getBook(id);
        if (book != null) {
            book.setPrice(newPrice);
            dao.updateBook(book);
            System.out.println("Book Updated");
        }
    }

    /* DELETE */
    public void deleteBook(int id) {
        dao.deleteBook(id);
        System.out.println("Book Deleted");
    }
}
