package com.ayush.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.bookstore.model.Order;
import com.ayush.bookstore.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

@Autowired
private OrderService service;

@GetMapping
public List<Order> getAll(){
return service.getOrders();
}

@PostMapping
public Order create(@RequestBody Order order){
return service.placeOrder(order);
}

}