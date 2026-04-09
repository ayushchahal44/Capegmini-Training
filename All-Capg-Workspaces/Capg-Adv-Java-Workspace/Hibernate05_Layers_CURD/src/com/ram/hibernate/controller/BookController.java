
package com.ram.hibernate.controller;

import com.ram.hibernate.service.BookService;

public class BookController {

    public static void main(String[] args) {

        BookService service = new BookService();

        // CREATE
        service.addBook(101, "Java", 250);

        // READ
        service.getBook(101);

        // UPDATE
        service.updateBookPrice(101, 650);

        // READ AGAIN
        service.getBook(101);

        // DELETE
        //service.deleteBook(101);
    }
}
