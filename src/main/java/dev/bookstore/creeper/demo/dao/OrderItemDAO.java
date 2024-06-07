package dev.bookstore.creeper.demo.dao;

import java.util.List;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.OrderItem;

public interface OrderItemDAO {
    List<OrderItem> findAllOrderItemsByBook(Book book);  
    void deleteOrderItem(OrderItem orderItem);
}
