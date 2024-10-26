package dev.bookstore.creeper.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.AuthenticationException;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.service.OrderService;
import dev.bookstore.creeper.demo.utils.SessionUtils;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderController(
            OrderService orderService,
            KafkaTemplate<String, String> kafkaTemplate) {
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        // 配置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping
    public ResponseEntity<Object> getOrders(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to) {
        try {
            return ResponseEntity.ok(orderService.getOrders(SessionUtils.getSessionUserId(), q, from, to));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new GeneralResponseDTO(false, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrders(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to) {
        try {
            return ResponseEntity.ok(orderService.getAllOrders(SessionUtils.getSessionUserId(), q, from, to));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new GeneralResponseDTO(false, e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new GeneralResponseDTO(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new GeneralResponseDTO(false, e.getMessage()));
        }
    }

}
