package dev.bookstore.creeper.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
    List<OrderItem> findAllByBook(Book book);
}
