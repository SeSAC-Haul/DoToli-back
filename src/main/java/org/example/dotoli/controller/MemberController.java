package org.example.dotoli.controller;

import org.example.dotoli.domain.Member;
import org.example.dotoli.dto.member.MemberResponseDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 정보 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

	@GetMapping("/me")
	public ResponseEntity<MemberResponseDto> getCurrentUserInfo(
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Member member = userDetails.getMember();

		return ResponseEntity.ok(
				new MemberResponseDto(member.getId(), member.getEmail(), member.getNickname())
		);
	}

}