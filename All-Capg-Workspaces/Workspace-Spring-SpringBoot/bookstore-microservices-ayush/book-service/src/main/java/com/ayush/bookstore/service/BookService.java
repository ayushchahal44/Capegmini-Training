package com.ayush.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayush.bookstore.model.Book;
import com.ayush.bookstore.repository.BookRepository;

@Service
public class BookService {

@Autowired
private BookRepository repo;

public List<Book> getAllBooks(){
return repo.findAll();
}

public Book getBook(Long id){
return repo.findById(id).orElseThrow();
}

public Book saveBook(Book book){
return repo.save(book);
}

public Book updateBook(Long id, Book book){
book.setId(id);
return repo.save(book);
}

public void deleteBook(Long id){
repo.deleteById(id);
}

}