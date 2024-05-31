package dev.bookstore.creeper.demo.serviceimpl;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;

import dev.bookstore.creeper.demo.dao.BookDAO;
import dev.bookstore.creeper.demo.dao.CartItemDAO;
import dev.bookstore.creeper.demo.dao.UserDAO;
import dev.bookstore.creeper.demo.dto.CartItemDTO;
import dev.bookstore.creeper.demo.dto.GetItemsOkDTO;
import dev.bookstore.creeper.demo.model.Book;
import dev.bookstore.creeper.demo.model.CartItem;
import dev.bookstore.creeper.demo.model.User;
import dev.bookstore.creeper.demo.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final CartItemDAO cartItemDAO;

    public CartServiceImpl(
            UserDAO userDAO,
            BookDAO bookDAO,
            CartItemDAO cartItemDAO) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public GetItemsOkDTO<CartItemDTO> getCartItems(int userId) {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
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
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Book book = bookDAO
                .findBookById(bookId)
                .orElseThrow(
                        () -> new NoSuchElementException("Book not found"));

        if (cartItemDAO.findCartItemByBookAndUser(book, user).isPresent()) {
            // 购物车中已经有这本书
            throw new IllegalArgumentException("Book already in cart");
        }

        CartItem cartItem = new CartItem(book, user, 1);
        cartItemDAO.saveCartItem(cartItem);

        user.getCartItems().add(cartItem);
        userDAO.saveUser(user);

        return cartItem.getId();
    }

    @Override
    public void updateCartItem(
            int userId,
            Integer cartItemId,
            Integer number) throws AuthenticationException {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        CartItem cartItem = cartItemDAO.findCartItemById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("cartItem not found"));

        if (!cartItem.getUser().equals(user)) {
            throw new AuthenticationException("User not authorized");
        }
        cartItem.setNumber(number);
        cartItemDAO.saveCartItem(cartItem);
    }

    @Override
    public void deleteCartItem(
            int userId,
            Integer cartItemId) throws AuthenticationException {
        User user = userDAO.findUserById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        CartItem cartItem = cartItemDAO.findCartItemById(cartItemId)
                .orElseThrow(() -> new NoSuchElementException("cartItem not found"));
        if (!cartItem.getUser().equals(user)) {
            throw new AuthenticationException("User not authorized");
        }

        cartItemDAO.deleteCartItem(cartItem);
    }
}
