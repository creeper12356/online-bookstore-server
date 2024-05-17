package dev.bookstore.creeper.demo.controller;

import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    public ResponseEntity<Object> getOrders(
        @CookieValue String token
    ) {
        try {
            return ResponseEntity.ok(orderService.getOrders(token));
        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new GeneralResponseDTO(false, e.getMessage())
            );
        }
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(
        @CookieValue String token,
        @RequestBody CreateOrderRequestDTO dto
    ) {
        try {
            orderService.createOrder(token, dto);
            return ResponseEntity.ok(new GeneralResponseDTO(true, "Order created"));
        } catch(AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new GeneralResponseDTO(false, e.getMessage())
            );
        } catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new GeneralResponseDTO(false, e.getMessage())
            );
        }
    }

}
