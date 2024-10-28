package org.example.dotoli.config.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {

	private final Map<Long, Set<WebSocketSession>> teamSessions = new ConcurrentHashMap<>();

	public void addSession(Long teamId, WebSocketSession session) {
		teamSessions.computeIfAbsent(teamId, k -> ConcurrentHashMap.newKeySet()).add(session);
	}

	public void removeSession(Long teamId, WebSocketSession session) {
		Set<WebSocketSession> sessions = teamSessions.get(teamId);
		if (sessions != null) {
			sessions.remove(session);
			if (sessions.isEmpty()) {
				teamSessions.remove(teamId);
			}
		}
	}

	public Set<WebSocketSession> getTeamSessions(Long teamId) {
		return teamSessions.getOrDefault(teamId, ConcurrentHashMap.newKeySet());
	}

}
