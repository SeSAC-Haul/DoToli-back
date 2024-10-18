package org.example.dotoli.controller;

import java.util.List;

import org.example.dotoli.dto.member.MemberResponseDto;
import org.example.dotoli.dto.team.TeamRequestDto;
import org.example.dotoli.dto.team.TeamResponseDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 팀 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

	private final TeamService teamService;

	/**
	 * 새로운 팀 생성
	 */
	@PostMapping
	public ResponseEntity<Long> createNewTeam(
			@RequestBody TeamRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamService.createTeam(userDetails.getMember().getId(), dto));
	}

	/**
	 * 현재 로그인한 사용자의 팀 목록 조회
	 */
	@GetMapping
	public ResponseEntity<List<TeamResponseDto>> getCurrentMemberTeamList(
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamService.getCurrentMemberTeamList(userDetails.getMember().getId()));
	}

	/**
	 * 특정 팀의 정보 조회
	 */
	@GetMapping("/{teamId}")
	public ResponseEntity<TeamResponseDto> getTeamInfo(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long teamId
	) {
		return ResponseEntity.ok(teamService.getTeamInfo(userDetails.getMember().getId(), teamId));
	}

	/**
	 * 특정 팀의 멤버 목록 조회
	 */
	@GetMapping("/{teamId}/members")
	public ResponseEntity<List<MemberResponseDto>> getTeamMembers(@PathVariable Long teamId) {
		return ResponseEntity.ok(teamService.getMembersByTeamId(teamId));
	}

}
