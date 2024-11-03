package org.example.dotoli.config.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("#{${app.frontend.base-urls}}")
	private List<String> frontendBaseUrls;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(taskWebSocketHandler, "/ws/tasks/{teamId}")
				.setAllowedOrigins(frontendBaseUrls.toArray(new String[0]))
				.addInterceptors(webSocketAuthInterceptor);
	}

}
