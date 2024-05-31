package dev.bookstore.creeper.demo.dao;

import java.util.Optional;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.User;

public interface CartItemDAO {
    Optional<CartItem> findByBookAndUser(Book book, User user);
}
