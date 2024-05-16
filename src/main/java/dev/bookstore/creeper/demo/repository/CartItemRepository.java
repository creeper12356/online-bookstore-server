package dev.bookstore.creeper.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByBookAndUser(Book book, User user);
}
