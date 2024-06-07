package dev.bookstore.creeper.demo.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import dev.bookstore.creeper.demo.dao.CartItemDAO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.CartItemRepository;

@Component
public class CartItemDAOImpl implements CartItemDAO {
    private final CartItemRepository cartItemRepository;

    public CartItemDAOImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Optional<CartItem> findCartItemByBookAndUser(Book book, User user) {
        return cartItemRepository.findByBookAndUser(book, user);
    }

    @Override
    public Optional<CartItem> findCartItemById(Integer id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public List<CartItem> findAllCartItemsByBook(Book book) {
        return cartItemRepository.findAllByBook(book);
    }
}
