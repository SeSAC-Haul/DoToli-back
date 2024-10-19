package org.example.dotoli.controller;

import java.util.List;

import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.TeamTaskValidation;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.TeamTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/teams")
public class TeamTaskController {

	private final TeamTaskService teamTaskService;

	/**
	 * 간단한 팀 할 일 추가
	 */
	@PostMapping("/tasks/simple")
	public ResponseEntity<Long> addSimpleTask(
			@RequestBody @Validated(TeamTaskValidation.class) TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamTaskService.createSimpleTask(dto, userDetails.getMember().getId()));
	}

	/**
	 * 상세한 팀 할 일 추가
	 */
	@PostMapping("/tasks/detailed")
	public ResponseEntity<Long> addDetailedTask(
			@RequestBody @Validated(TeamTaskValidation.class) TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamTaskService.createDetailedTask(dto, userDetails.getMember().getId()));
	}

	/**
	 * 특정 팀의 모든 할 일 목록 조회
	 */
	@GetMapping("/{teamId}/tasks")
	public ResponseEntity<List<TaskResponseDto>> getAllTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long teamId
	) {
		return ResponseEntity.ok(teamTaskService.getAllTasksByTeamId(userDetails.getMember().getId(), teamId));
	}

	/**
	 * 팀 할 일 수정
	 */
	@PutMapping("/tasks/{targetId}")
	public ResponseEntity<Void> updateTask(
			@PathVariable Long targetId,
			@RequestBody @Validated(TeamTaskValidation.class) TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.updateTask(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	/**
	 * 팀 할 일 완료 상태로 변경
	 */
	@PutMapping("/tasks/{targetId}/toggle")
	public ResponseEntity<Void> toggleTaskDone(
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
	@DeleteMapping("/tasks/{targetId}")
	public ResponseEntity<Void> deleteTask(
			@PathVariable Long targetId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.deleteTask(targetId, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

}
