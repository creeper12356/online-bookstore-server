package dev.bookstore.creeper.demo.controller;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.CreateCartItemOkResponseDTO;
import dev.bookstore.creeper.demo.dto.CreateCartItemRequestDTO;
import dev.bookstore.creeper.demo.dto.UpdateCartItemRequestDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Object> getCartItems(@CookieValue String token) {
        try {
            return ResponseEntity.ok(
                cartService.getCartItems(token)
            );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCartItem(
        @CookieValue String token, 
        @RequestBody CreateCartItemRequestDTO dto
    ) {
        try {
            Integer newCartItemId = cartService.createCartItem(token, dto.getBookId());
            return ResponseEntity.ok(
                new CreateCartItemOkResponseDTO(true, "Book added to cart", newCartItemId)
            );
        } catch(AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        } catch(NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCartItem(
        @CookieValue String token, 
        @PathVariable Integer id,
        @RequestBody UpdateCartItemRequestDTO dto
    ) {
        try {
            cartService.updateCartItem(token, id, dto.getNumber());
            return ResponseEntity.ok(
                new GeneralResponseDTO(true, "Cart item updated")
            );
        } catch(AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        } catch(NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        } catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCartItem(
        @CookieValue String token,
        @PathVariable Integer id
    ) {
        try {
            cartService.deleteCartItem(token, id);
            return ResponseEntity.ok(
                new GeneralResponseDTO(true, "Cart item deleted")
            );
        } catch(AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        } catch(NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                        new GeneralResponseDTO(false, e.getMessage())
                    );
        }
    }
}