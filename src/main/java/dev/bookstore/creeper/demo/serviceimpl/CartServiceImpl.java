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
import dev.bookstore.creeper.demo.service.AuthService;
import dev.bookstore.creeper.demo.service.CartService;

@Service
public class CartServiceImpl implements CartService {
    private AuthService authService;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(
        AuthService authService, 
        UserRepository userRepository,
        BookRepository bookRepository,
        CartItemRepository cartItemRepository
    ) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public GetItemsOkDTO<CartItemDTO> getCartItems(String token) throws AuthenticationException{
        User user;
        try {
            user = authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("User not found");
        }
        List<CartItemDTO> cartItems = user.getCartItems()
                                        .stream()
                                        .map(
                                            cartItem -> new CartItemDTO(cartItem)
                                        )
                                        .sorted(Comparator.comparing(CartItemDTO::getId).reversed())
                                        .collect(Collectors.toList());

        // TODO: 分页接口
        return new GetItemsOkDTO<>(cartItems.size(), cartItems);
    }

    @Override 
    public Integer createCartItem(String token, Integer bookId) throws AuthenticationException {
        User user;
        try {
            user = authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("User not found");
        }
        
        Book book = bookRepository
                    .findById(bookId)
                    .orElseThrow(
                        () -> new NoSuchElementException("Book not found")
                    );

        if(cartItemRepository.findByBookAndUser(book, user).isPresent()) {
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
        String token, 
        Integer id, 
        Integer number
    ) throws AuthenticationException 
    {
        User user;
        try {
            user = authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("User not found");
        }

        CartItem cartItem = cartItemRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("cartItem not found"));
        
        if(!cartItem.getUser().equals(user)) {
            throw new AuthenticationException("User not authorized");
        }
        cartItem.setNumber(number);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(
        String token,
        Integer id
    ) throws AuthenticationException {
        User user;
        try {
            user = authService.getUserByToken(token);
        } catch(NoSuchElementException e) {
            throw new AuthenticationException("User not authorized");
        }

        CartItem cartItem = cartItemRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("cartItem not found"));
        if(!cartItem.getUser().equals(user)) {
            throw new AuthenticationException("User not authorized");
        }

        cartItemRepository.delete(cartItem);
    }
}
