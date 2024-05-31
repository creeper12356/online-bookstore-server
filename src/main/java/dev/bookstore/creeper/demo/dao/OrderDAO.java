package dev.bookstore.creeper.demo.dao;

import dev.bookstore.creeper.demo.model.Order;

public interface OrderDAO {
    void saveOrder(Order order);
}
