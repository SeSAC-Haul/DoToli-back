package org.example.dotoli.dto.team;

import lombok.Data;

/**
 * 팀 생성 요청 정보를 담는 DTO 클래스
 */
@Data
public class TeamRequestDto {

	private String teamName;

	public TeamRequestDto(String teamName) {
		this.teamName = teamName;
	}

}
