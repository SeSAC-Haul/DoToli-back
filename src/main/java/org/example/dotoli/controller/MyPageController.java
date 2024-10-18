package org.example.dotoli.controller;

import org.example.dotoli.domain.Member;
import org.example.dotoli.dto.member.MyPageResponseDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 마이페이지 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping
	public ResponseEntity<MyPageResponseDto> getMyPageInfo(
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Member member = userDetails.getMember();
		MyPageResponseDto dto = myPageService.getMyPageInfo(member.getId());
		dto.setMemberInfo(member.getEmail(), member.getNickname());
		return ResponseEntity.ok(dto);
	}

}
