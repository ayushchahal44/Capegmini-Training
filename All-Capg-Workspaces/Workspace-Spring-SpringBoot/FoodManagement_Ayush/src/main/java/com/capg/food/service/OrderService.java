package com.capg.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.capg.food.dao.*;
import com.capg.food.entity.*;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private FoodItemDAO foodDAO;

    public void placeOrder(List<Long> ids, String customer) {

        List<FoodItem> items = new ArrayList<>();

        for (Long id : ids)
            items.add(foodDAO.findById(id));

        FoodOrder order = new FoodOrder();
        order.setCustomerName(customer);
        order.setOrderDate(new Date());
        order.setFoodItems(items);

        double total = items.stream()
                .mapToDouble(FoodItem::getPrice)
                .sum();

        order.setTotalAmount(total);

        orderDAO.save(order);
    }

    public double calculateTotal(Long orderId) {

        FoodOrder order = orderDAO.findById(orderId);

        if (order == null) {
            System.out.println("Order Not Found!");
            return 0;
        }

        return order.getTotalAmount();
    }
}