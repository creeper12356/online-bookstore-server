package dev.bookstore.creeper.demo.serviceimpl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dto.CartItemDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.repository.BookRepository;
import dev.bookstore.creeper.demo.repository.CartItemRepository;
import dev.bookstore.creeper.demo.repository.UserRepository;
import dev.bookstore.creeper.demo.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(
            UserRepository userRepository,
            BookRepository bookRepository,
            CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public GetItemsOkDTO<CartItemDTO> getCartItems(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        List<CartItemDTO> cartItems = user.getCartItems()
                .stream()
                .map(
                        cartItem -> new CartItemDTO(cartItem))
                .sorted(Comparator.comparing(CartItemDTO::getId).reversed())
                .collect(Collectors.toList());

        // TODO: 分页接口
        return new GetItemsOkDTO<>(cartItems.size(), cartItems);
    }

    @Override
    public Integer createCartItem(int userId, Integer bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(
                        () -> new NoSuchElementException("Book not found"));

        if (cartItemRepository.findByBookAndUser(book, user).isPresent()) {
            // 购物车中已经有这本书
            throw new IllegalArgumentException("Book already in cart");
        }

        CartItem cartItem = new CartItem(book, user, 1);
        cartItemRepository.save(cartItem);

        user.getCartItems().add(cartItem);
        userRepository.save(user);

        return cartItem.getId();
    }

    @Override
    public void updateCartItem(
            int userId,
            Integer cartItemId,
            Integer number) throws AuthenticationException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("cartItem not found"));

        if (!cartItem.getUser().equals(user)) {
            throw new AuthenticationException("User not authorized");
        }
        cartItem.setNumber(number);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(
            int userId,
            Integer cartItemId) throws AuthenticationException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("cartItem not found"));
        if (!cartItem.getUser().equals(user)) {
            throw new AuthenticationException("User not authorized");
        }

        cartItemRepository.delete(cartItem);
    }
}
