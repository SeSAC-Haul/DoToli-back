package org.example.dotoli.dto.member;

import java.util.ArrayList;
import java.util.List;

import org.example.dotoli.dto.invitation.PendingInvitationDto;

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

	private List<PendingInvitationDto> invitations;

	public MyPageResponseDto(
			Long totalTaskCount, Long completedTaskCount, Long achievementRate, List<PendingInvitationDto> invitations
	) {
		this.totalTaskCount = totalTaskCount;
		this.completedTaskCount = completedTaskCount;
		this.achievementRate = achievementRate;
		this.invitations = invitations != null ? invitations : new ArrayList<>(); // null 체크

	}

	public void setMemberInfo(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

}
