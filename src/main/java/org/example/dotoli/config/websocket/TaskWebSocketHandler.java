// TaskWebSocketHandler.java
package org.example.dotoli.config.websocket;

import java.io.IOException;

import org.example.dotoli.dto.websocket.TaskWebSocketMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;

	private final WebSocketSessionManager sessionManager;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		Long teamId = extractTeamId(session);
		sessionManager.addSession(teamId, session);
		log.info("WebSocket connection established for team: {}", teamId);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		Long teamId = extractTeamId(session);
		sessionManager.removeSession(teamId, session);
		log.info("WebSocket connection closed for team: {}", teamId);
	}

	public void broadcastTaskUpdate(TaskWebSocketMessage message) {
		String payload;
		try {
			payload = objectMapper.writeValueAsString(message);
		} catch (IOException e) {
			log.error("Failed to serialize message", e);
			return;
		}

		sessionManager.getTeamSessions(message.getTeamId()).forEach(session -> {
			try {
				session.sendMessage(new TextMessage(payload));
			} catch (IOException e) {
				log.error("Failed to send message to session", e);
			}
		});
	}

	private Long extractTeamId(WebSocketSession session) {
		String path = session.getUri().getPath();
		String[] segments = path.split("/");
		return Long.parseLong(segments[segments.length - 1]);
	}

}
