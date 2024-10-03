package org.example.dotoli.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰을 검증하고 인증을 처리하는 필터 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			final String jwt = authHeader.substring(7);
			final String loginId = jwtProvider.extractUsername(jwt);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (loginId != null && authentication == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginId);

				if (jwtProvider.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			filterChain.doFilter(request, response);
		} catch (MalformedJwtException e) {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token format");
		} catch (ExpiredJwtException e) {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
		} catch (SignatureException e) {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token signature");
		} catch (Exception e) {
			sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
		}
	}

	private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("status", status);
		errorDetails.put("message", message);

		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(errorDetails));
	}

}