package org.example.dotoli.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.example.dotoli.dto.auth.SignInRequestDto;
import org.example.dotoli.dto.auth.SignInResponseDto;
import org.example.dotoli.dto.auth.SignUpRequestDto;
import org.example.dotoli.security.jwt.JwtProvider;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	private final JwtProvider jwtProvider;

	@Value("${app.frontend.base-url}")
	private String frontendBaseUrl;

	@PostMapping("/signup")
	public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpRequestDto dto) {
		return ResponseEntity.ok(authenticationService.saveMember(dto));
	}

	@PostMapping("/signin")
	public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto dto) {
		CustomUserDetails userDetails = authenticationService.authenticate(dto);

		String jwtToken = jwtProvider.generateToken(userDetails);

		return ResponseEntity.ok(new SignInResponseDto(jwtToken, jwtProvider.getExpirationTime()));
	}

	/**
	 * 이메일 인증 요청
	 */
	@GetMapping("/verify-email")
	public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam String email) {
		try {
			authenticationService.sendVerificationEmail(email);
			Map<String, String> response = new HashMap<>();
			response.put("message", "이메일 인증 요청이 발송되었습니다.");
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			Map<String, String> response = new HashMap<>();
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	/**
	 * 토큰 및 이메일 인증 절차
	 */
	@GetMapping("/verify")
	public void verifyEmail(@RequestParam String token, HttpServletResponse response) throws IOException {
		try {
			authenticationService.verifyEmailToken(token);
			response.sendRedirect(frontendBaseUrl + "/email-verified?status=success");
		} catch (IllegalArgumentException e) {
			response.sendRedirect(frontendBaseUrl + "/email-verified?status=failure&message=" + e.getMessage());
		}
	}

}
