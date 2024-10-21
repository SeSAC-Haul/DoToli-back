package org.example.dotoli.controller;

import java.util.List;

import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.TeamTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 팀 Task 항목 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams/{teamId}/tasks")
public class TeamTaskController {

	private final TeamTaskService teamTaskService;

	/**
	 * 할 일 추가
	 */
	@PostMapping
	public ResponseEntity<Long> addTask(
			@PathVariable Long teamId,
			@RequestBody TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamTaskService.createTask(dto, userDetails.getMember().getId(), teamId));
	}

	/**
	 * 특정 팀의 모든 할 일 목록 조회
	 */
	@GetMapping
	public ResponseEntity<List<TaskResponseDto>> getAllTask(
			@PathVariable Long teamId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamTaskService.getAllTasksByTeamId(userDetails.getMember().getId(), teamId));
	}

	/**
	 * 팀 할 일 수정
	 */
	@PutMapping("/{targetId}")
	public ResponseEntity<Void> updateTask(
			@PathVariable Long teamId,
			@PathVariable Long targetId,
			@RequestBody TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.updateTask(targetId, dto, userDetails.getMember().getId(), teamId);

		return ResponseEntity.ok().build();
	}

	/**
	 * 팀 할 일 완료 상태로 변경
	 */
	@PutMapping("/{targetId}/toggle")
	public ResponseEntity<Void> toggleTaskDone(
			@PathVariable Long teamId,
			@PathVariable Long targetId,
			@RequestBody @Valid ToggleRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.toggleDone(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	/**
	 * 팀 할 일 삭제
	 */
	@DeleteMapping("/{targetId}")
	public ResponseEntity<Void> deleteTask(
			@PathVariable Long teamId,
			@PathVariable Long targetId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.deleteTask(targetId, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

}
