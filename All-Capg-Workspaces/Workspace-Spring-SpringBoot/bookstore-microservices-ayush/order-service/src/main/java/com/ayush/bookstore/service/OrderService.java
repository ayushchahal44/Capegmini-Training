package com.ayush.bookstore.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayush.bookstore.client.BookClient;
import com.ayush.bookstore.dto.BookDTO;
import com.ayush.bookstore.model.Order;
import com.ayush.bookstore.repository.OrderRepository;

@Service
public class OrderService {

@Autowired
private OrderRepository repo;

@Autowired
private BookClient bookClient;

public Order placeOrder(Order order){

BookDTO book = bookClient.getBookById(order.getBookId());

order.setTotalPrice(book.getPrice()*order.getQuantity());
order.setStatus("PLACED");
order.setOrderDate(LocalDate.now());

return repo.save(order);
}

public List<Order> getOrders(){
return repo.findAll();
}

}