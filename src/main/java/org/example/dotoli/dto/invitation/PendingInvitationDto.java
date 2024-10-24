package org.example.dotoli.dto.invitation;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 초대 수신 목록 정보를 담는 DTO 클래스
 */
@Data
@NoArgsConstructor
public class PendingInvitationDto {

	private Long id;

	private Long teamId;

	private String teamName;

	private String inviterNickname;

	private String inviterEmail;

	public PendingInvitationDto(Long id, Long teamId, String teamName, String inviterNickname, String inviterEmail) {
		this.id = id;
		this.teamId = teamId;
		this.teamName = teamName;
		this.inviterNickname = inviterNickname;
		this.inviterEmail = inviterEmail;
	}

}
