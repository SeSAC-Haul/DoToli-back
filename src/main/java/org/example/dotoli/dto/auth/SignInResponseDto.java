package org.example.dotoli.dto.auth;

import lombok.Data;

/**
 * 로그인 응답 정보를 담는 DTO 클래스
 */
@Data
public class SignInResponseDto {

	private String token;

	private long expiresIn;

	public SignInResponseDto(String token, long expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

}