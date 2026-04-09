package com.capg.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.springboot.client.ProductClient;
import com.capg.springboot.dto.OrderRequestDTO;
import com.capg.springboot.dto.ProductDTO;

@Service
public class OrderService {

    @Autowired
    private ProductClient productClient;

    //  METHOD SIGNATURE MUST MATCH CONTROLLER
    public String placeOrder(OrderRequestDTO request) {

        List<ProductDTO> products = productClient.getProducts();

        ProductDTO selectedProduct = products.stream()
                .filter(p -> p.getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double totalAmount = selectedProduct.getPrice() * request.getQuantity();

        return "Order placed successfully | Product: "
                + selectedProduct.getName()
                + " | Qty: " + request.getQuantity()
                + " | Total: " + totalAmount;
    }
}