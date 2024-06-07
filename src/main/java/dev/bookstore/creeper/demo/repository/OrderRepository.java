package dev.bookstore.creeper.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.Order;



@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>  {
}
