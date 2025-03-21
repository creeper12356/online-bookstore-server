package dev.bookstore.creeper.demo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;
import dev.bookstore.creeper.demo.service.OrderService;

@Component
public class OrderConsumer {
    private final Gson gson = new Gson();
    @Autowired
    private OrderService orderService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "createOrderTopic", groupId = "my-group")
    public void handleCreateOrder(@Payload String dtoJsonString,
            @Header(name = KafkaHeaders.RECEIVED_KEY) String correlationId) {
        GeneralResponseDTO response;
        try {
            CreateOrderRequestDTO dto = gson.fromJson(dtoJsonString, CreateOrderRequestDTO.class);
            orderService.createOrder(dto.getUserId(), dto);
            response = new GeneralResponseDTO(true, "下单成功");
        } catch (Exception e) {
            response = new GeneralResponseDTO(false, e.getMessage());
        }

        kafkaTemplate.send("createOrderReturnTopic", correlationId, gson.toJson(response));
    }
}
