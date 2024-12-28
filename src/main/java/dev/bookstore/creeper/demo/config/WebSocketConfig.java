package dev.bookstore.creeper.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import dev.bookstore.creeper.demo.websocket.OrderWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(this.orderWebSocketHandler(), "/orders")
			.setAllowedOrigins("*");
	}

	@Bean
	public OrderWebSocketHandler orderWebSocketHandler() {
		return new OrderWebSocketHandler();
	}
}