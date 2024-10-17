package org.example.dotoli.service;

import org.example.dotoli.config.error.exception.DuplicateEmailException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.dto.auth.SignInRequestDto;
import org.example.dotoli.dto.auth.SignUpRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * 회원가입
	 */
	@Transactional
	public Long saveMember(SignUpRequestDto dto) {
		checkDuplicateEmail(dto.getEmail());
		Member member = Member.createNew(
				dto.getEmail(), passwordEncoder.encode(dto.getPassword()), dto.getNickname()
		);

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

}
