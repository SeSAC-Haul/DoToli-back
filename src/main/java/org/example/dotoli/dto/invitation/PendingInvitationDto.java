package org.example.dotoli.dto.invitation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PendingInvitationDto {

	private Long id;

	private String teamName;

	private String inviterNickname;

	private String inviterEmail;

	public PendingInvitationDto(Long id, String teamName, String inviterNickname, String inviterEmail) {
		this.id = id;
		this.teamName = teamName;
		this.inviterNickname = inviterNickname;
		this.inviterEmail = inviterEmail;
	}

}
