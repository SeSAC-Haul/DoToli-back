package org.example.dotoli.security.userdetails;

import org.example.dotoli.domain.Member;
import org.example.dotoli.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * 사용자 상세 정보를 로드하는 커스텀 서비스 클래스
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new CustomUserDetails(member);
	}

}
