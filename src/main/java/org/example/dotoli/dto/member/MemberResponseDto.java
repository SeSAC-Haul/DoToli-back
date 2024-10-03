package org.example.dotoli.dto.member;

import lombok.Data;

/**
 * 회원 정보 응답을 담는 DTO 클래스
 */
@Data
public class MemberResponseDto {

	private Long id;

	private String email;

	private String nickname;

	public MemberResponseDto(Long id, String email, String nickname) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
	}

}
