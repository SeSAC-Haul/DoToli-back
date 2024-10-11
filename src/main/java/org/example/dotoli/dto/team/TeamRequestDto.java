package org.example.dotoli.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 팀 생성 요청 정보를 담는 DTO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestDto {

	private String teamName;

}
