package dev.bookstore.creeper.demo.dao;

import java.util.List;

import dev.bookstore.creeper.demo.model.Order;

public interface OrderDAO {
    void saveOrder(Order order);
    List<Order> findAllOrders();
}
