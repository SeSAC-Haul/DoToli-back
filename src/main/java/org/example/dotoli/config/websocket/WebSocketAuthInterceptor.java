package org.example.dotoli.config.websocket;

import java.util.Map;

import org.example.dotoli.security.jwt.JwtProvider;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.security.userdetails.CustomUserDetailsService;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

	private final JwtProvider jwtProvider;

	private final CustomUserDetailsService userDetailsService;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, Map<String, Object> attributes) {
		try {
			String token = extractToken(request);
			if (token != null) {
				String email = jwtProvider.extractUsername(token);
				CustomUserDetails userDetails = (CustomUserDetails)userDetailsService.loadUserByUsername(email);

				if (jwtProvider.isTokenValid(token, userDetails)) {
					attributes.put("email", email);
					return true;
				}
			}
		} catch (Exception e) {
			log.error("WebSocket handshake failed", e);
		}
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, Exception exception) {
	}

	private String extractToken(ServerHttpRequest request) {
		String query = request.getURI().getQuery();
		if (query != null) {
			String[] params = query.split("&");
			for (String param : params) {
				String[] keyValue = param.split("=");
				if (keyValue.length == 2 && "token".equals(keyValue[0])) {
					return keyValue[1];
				}
			}
		}
		return null;
	}

}
