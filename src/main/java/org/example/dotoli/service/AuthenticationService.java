package org.example.dotoli.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.dotoli.config.error.exception.DuplicateEmailException;
import org.example.dotoli.domain.EmailToken;
import org.example.dotoli.domain.Member;
import org.example.dotoli.dto.auth.SignInRequestDto;
import org.example.dotoli.dto.auth.SignUpRequestDto;
import org.example.dotoli.repository.EmailTokenRepository;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * 인증 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final EmailTokenRepository emailTokenRepository;

	private final JavaMailSender mailSender;

	/**
	 * 회원가입
	 */
	@Transactional
	public Long saveMember(SignUpRequestDto dto) {
		checkDuplicateEmail(dto.getEmail());
		EmailToken emailToken = (EmailToken)emailTokenRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new RuntimeException("Email not verified"));

		if (!emailToken.isEmailVerified()) {
			throw new RuntimeException("Email not verified");
		}

		Member member = Member.createNew(
				dto.getEmail(), passwordEncoder.encode(dto.getPassword()), dto.getNickname()
		);

		emailTokenRepository.delete(emailToken);

		return memberRepository.save(member).getId();
	}

	/**
	 * 로그인
	 */
	public CustomUserDetails authenticate(SignInRequestDto dto) {
		// TODO 로그인 실패 시 발생하는 예외 처리
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
		);

		return (CustomUserDetails)authenticate.getPrincipal();
	}

	/**
	 * 이메일 중복 체크
	 */
	private void checkDuplicateEmail(String email) {
		// TODO EXISTS 쿼리 사용 고려
		memberRepository.findByEmail(email)
				.ifPresent(m -> {
					throw new DuplicateEmailException();
				});
	}

	/**
	 * 이메일 중복 체크 및 인증 이메일 전송
	 */
	@Transactional
	public void sendVerificationEmail(String email) {
		checkDuplicateEmail(email);
		String token = UUID.randomUUID().toString();
		EmailToken emailToken = new EmailToken(email, token, LocalDateTime.now().plusHours(1), false);
		emailTokenRepository.save(emailToken);

		String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(email);
			helper.setSubject("DoToli 회원가입 인증 메일");

			String emailContent = "<p>아래 링크를 클릭하여 이메일 인증을 완료해주세요</p><br>" +
					"<a href=\"" + verificationLink + "\">이메일 인증하기</a>";

			helper.setText(emailContent, true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new IllegalStateException("Failed to send email", e);
		}
	}

	/**
	 * 이메일 토큰 검증
	 */
	@Transactional
	public void verifyEmailToken(String token) {
		EmailToken emailToken = (EmailToken)emailTokenRepository.findByToken(token)
				.orElseThrow(() -> new IllegalArgumentException("Invalid token."));

		if (emailToken.isExpired()) {
			throw new IllegalArgumentException("The token has expired.");
		}

		String email = emailToken.getEmail();
		if (memberRepository.findByEmail(email).isPresent()) {
			throw new IllegalArgumentException("Email is already registered.");
		}

		emailToken.verifyEmail();
	}

}
