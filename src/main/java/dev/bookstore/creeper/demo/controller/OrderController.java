package dev.bookstore.creeper.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.naming.AuthenticationException;

import org.apache.kafka.common.Uuid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.service.OrderService;
import dev.bookstore.creeper.demo.utils.SessionUtils;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ConcurrentMap<String, CompletableFuture<ResponseEntity<Object>>> futures = new ConcurrentHashMap<>();

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

    @PostMapping
    public ResponseEntity<Object> createOrder(
            @RequestBody CreateOrderRequestDTO dto) {
        System.out.println("OrderController.createOrder");
        String correlationId = Uuid.randomUuid().toString();
        CompletableFuture<ResponseEntity<Object>> future = new CompletableFuture<>();
        futures.put(correlationId, future);

        try {
            dto.setUserId(SessionUtils.getSessionUserId());
        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new GeneralResponseDTO(false, e.getMessage()));
        }
        kafkaTemplate.send("createOrderTopic",
                correlationId,
                new Gson().toJson(dto));
        try {
            return future.get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new GeneralResponseDTO(false, e.getMessage()));
        }
    }

    @KafkaListener(topics = "createOrderReturnTopic", groupId = "my-group")
    public void handleCreateOrderReturn(@Payload String message,
            @Header(name = KafkaHeaders.RECEIVED_KEY) String correlationId) {
        CompletableFuture<ResponseEntity<Object>> future = futures.remove(correlationId);
        GeneralResponseDTO response = new Gson().fromJson(message, GeneralResponseDTO.class);
        if (response.getOk()) {
            future.complete(ResponseEntity.ok(response));
        } else {
            future.complete(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
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
