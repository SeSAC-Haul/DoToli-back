package org.example.dotoli.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

	private final TaskWebSocketHandler taskWebSocketHandler;

	private final WebSocketAuthInterceptor webSocketAuthInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(taskWebSocketHandler, "/ws/tasks/{teamId}")
				.setAllowedOrigins("http://localhost:5173")
				.addInterceptors(webSocketAuthInterceptor);
	}

}
