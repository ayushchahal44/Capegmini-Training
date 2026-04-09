package com.ayush.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}