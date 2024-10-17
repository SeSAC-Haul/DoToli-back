package org.example.dotoli.dto.member;

import lombok.Data;

/**
 * MyPage 정보를 담는 DTO 클래스
 */
@Data
public class MyPageResponseDto {

	private String email;

	private String nickname;

	private Long totalTaskCount;

	private Long completedTaskCount;

	private Long achievementRate;

	public MyPageResponseDto(
			Long totalTaskCount, Long completedTaskCount, Long achievementRate
	) {
		this.totalTaskCount = totalTaskCount;
		this.completedTaskCount = completedTaskCount;
		this.achievementRate = achievementRate;
	}

	public void setMemberInfo(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

}
