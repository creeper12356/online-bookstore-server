package dev.bookstore.creeper.demo.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.NonNull;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import dev.bookstore.creeper.demo.dto.CreateOrderRequestDTO;
import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;

@EnableWebSocketMessageBroker
public class OrderWebSocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("websocket connection established, session_id = " + session.getId());
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        try {
            // NOTE: 在websocket连接中，直接通过userId判断发起请求的用户，具有安全问题
            CreateOrderRequestDTO dto = gson.fromJson(message.getPayload(), CreateOrderRequestDTO.class);
            System.out.println(dto);
            // TODO: modify dto members

            kafkaTemplate.send("createOrderTopic", session.getId(), gson.toJson(dto));
            sessions.put(session.getId(), session);
        } catch (Exception e) {
            sendMessageWrapper(session, gson.toJson(new GeneralResponseDTO(false, e.getMessage())));
        }

    }

    @KafkaListener(topics = "createOrderReturnTopic", groupId = "my-group")
    public void handleCreateOrderReturn(@Payload String message,
            @Header(name = KafkaHeaders.RECEIVED_KEY) String sessionId) {
        WebSocketSession session = sessions.remove(sessionId);
        GeneralResponseDTO response = gson.fromJson(message, GeneralResponseDTO.class);
        sendMessageWrapper(session, gson.toJson(response));
    }

    private void sendMessageWrapper(WebSocketSession session, String messageString) {
        try {
            session.sendMessage(new TextMessage(messageString));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
