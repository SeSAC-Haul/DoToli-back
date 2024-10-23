package org.example.dotoli.controller;

import java.time.LocalDate;

import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.TeamTaskValidation;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.TeamTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 팀 Task 항목 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Slf4j
public class TeamTaskController {

	private final TeamTaskService teamTaskService;

	/**
	 * 할 일 추가
	 */
	@PostMapping("/tasks")
	public ResponseEntity<Long> addTask(
			@RequestBody @Validated(TeamTaskValidation.class) TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamTaskService.createTask(dto, userDetails.getMember().getId()));
	}

	/**
	 * 특정 팀의 모든 할 일 목록 조회
	 */
	@GetMapping("/{teamId}/tasks")
	public ResponseEntity<Page<TaskResponseDto>> getAllTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long teamId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<TaskResponseDto> tasks = teamTaskService.getAllTasksByTeamId(userDetails.getMember().getId(), teamId,
				pageable);
		return ResponseEntity.ok(tasks);
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

	/**
	 * 팀 할 일 조건 별로 선택된 정렬 조회
	 */
	@GetMapping("/{teamId}/tasks/filter")
	public ResponseEntity<Page<TaskResponseDto>> filterTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long teamId,
			@RequestParam(required = false) LocalDate startDate,
			@RequestParam(required = false) LocalDate endDate,
			@RequestParam(required = false) LocalDate deadline,
			@RequestParam(required = false) Boolean flag,
			@RequestParam(required = false) LocalDate createdAt,
			@RequestParam(required = false) Boolean done,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page
	) {
		int size = 5;
		Pageable pageable = PageRequest.of(page, size);

		Page<TaskResponseDto> filteredTasks = teamTaskService.filterTask(
				userDetails.getMember().getId(), pageable, teamId,
				startDate, endDate, deadline, flag, createdAt, done, keyword);

		return ResponseEntity.ok(filteredTasks);
	}

	/**
	 *  팀 할 일 검색
	 */
	@GetMapping("/{teamId}/tasks/search")
	public ResponseEntity<Page<TaskResponseDto>> searchTask(
			@PathVariable Long teamId,
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page
	) {
		log.info("키워드: {}", keyword);

		int size = 5;
		Pageable pageable = PageRequest.of(page, size);

		Page<TaskResponseDto> filteredTasks = teamTaskService.searchTask(
				userDetails.getMember().getId(), teamId, pageable, keyword);

		return ResponseEntity.ok(filteredTasks);
	}

}
