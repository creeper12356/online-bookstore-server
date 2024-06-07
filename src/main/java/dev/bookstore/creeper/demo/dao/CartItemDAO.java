package dev.bookstore.creeper.demo.dao;

import java.util.List;
import java.util.Optional;

import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.User;

public interface CartItemDAO {
    Optional<CartItem> findCartItemByBookAndUser(Book book, User user);
    Optional<CartItem> findCartItemById(Integer id);
    List<CartItem> findAllCartItemsByBook(Book book);
    void saveCartItem(CartItem cartItem);
    void deleteCartItem(CartItem cartItem);
}
