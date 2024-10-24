package org.example.dotoli.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 이메일 인증 토큰 엔티티 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EmailToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String token;

	private LocalDateTime expiryDate;

	private boolean isEmailVerified = false;

	public EmailToken(String email, String token, LocalDateTime expiryDate, boolean isEmailVerified) {
		this.email = email;
		this.token = token;
		this.expiryDate = expiryDate;
		this.isEmailVerified = isEmailVerified;
	}

	public void verifyEmail() {
		this.isEmailVerified = true;
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expiryDate);
	}

}
