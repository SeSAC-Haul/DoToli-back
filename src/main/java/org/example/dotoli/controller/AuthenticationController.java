package org.example.dotoli.controller;

import org.example.dotoli.dto.auth.SignInRequestDto;
import org.example.dotoli.dto.auth.SignInResponseDto;
import org.example.dotoli.dto.auth.SignUpRequestDto;
import org.example.dotoli.security.jwt.JwtProvider;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
