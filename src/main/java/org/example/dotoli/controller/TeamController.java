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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

	private final TeamService teamService;

	@PostMapping
	public ResponseEntity<Long> createNewTeam(
			@RequestBody TeamRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamService.createTeam(userDetails.getMember().getId(), dto));
	}

	@GetMapping
	public ResponseEntity<List<TeamResponseDto>> getCurrentMemberTeamList(
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamService.getCurrentMemberTeamList(userDetails.getMember().getId()));
	}

	@GetMapping("/{teamId}")
	public ResponseEntity<TeamResponseDto> getTeamInfo(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long teamId
	) {
		return ResponseEntity.ok(teamService.getTeamInfo(userDetails.getMember().getId(), teamId));
	}

	@GetMapping("/{teamId}/members")
	public ResponseEntity<List<MemberResponseDto>> getTeamMembers(@PathVariable Long teamId) {
		return ResponseEntity.ok(teamService.getMembersByTeamId(teamId));
	}

}
