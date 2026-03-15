package com.ayush.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.bookstore.model.Book;
import com.ayush.bookstore.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

@Autowired
private BookService service;

@GetMapping
public List<Book> getAll(){
return service.getAllBooks();
}

@GetMapping("/{id}")
public Book getOne(@PathVariable Long id){
return service.getBook(id);
}

@PostMapping
public Book create(@RequestBody Book book){
return service.saveBook(book);
}

@PutMapping("/{id}")
public Book update(@PathVariable Long id,@RequestBody Book book){
return service.updateBook(id,book);
}

@DeleteMapping("/{id}")
public void delete(@PathVariable Long id){
service.deleteBook(id);
}
}