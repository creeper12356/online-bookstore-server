package dev.bookstore.creeper.demo.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import dev.bookstore.creeper.demo.dto.GeneralResponseDTO;

@EnableWebSocketMessageBroker
public class OrderWebSocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("websocket connection established, session_id = " + session.getId());
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) {
        try {
            // NOTE: 在websocket连接中，直接通过userId判断发起请求的用户，具有安全问题
            // CreateOrderRequestDTO dto = new Gson().fromJson(message.getPayload(), CreateOrderRequestDTO.class);

            sessions.put(session.getId(), session);
            // TODO: 下订单功能暂时不可用

        } catch (Exception e) {
            sendMessageWrapper(session, new Gson().toJson(new GeneralResponseDTO(false, e.getMessage())));
        }

    }

    // @KafkaListener(topics = "createOrderReturnTopic", groupId = "my-group")
    // public void handleCreateOrderReturn(@Payload String message,
    //         @Header(name = KafkaHeaders.RECEIVED_KEY) String sessionId) {
    //     WebSocketSession session = sessions.remove(sessionId);
    //     GeneralResponseDTO response = new Gson().fromJson(message, GeneralResponseDTO.class);
    //     sendMessageWrapper(session, new Gson().toJson(response));
    // }

    private void sendMessageWrapper(WebSocketSession session, String messageString) {
        try {
            session.sendMessage(new TextMessage(messageString));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
