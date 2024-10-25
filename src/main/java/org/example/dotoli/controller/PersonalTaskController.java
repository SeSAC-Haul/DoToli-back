package org.example.dotoli.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.PersonalTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 * Task 항목 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks") // TODO: URI 고민 필요, /api/personal/tasks
@Slf4j
public class PersonalTaskController {

	private final PersonalTaskService personalTaskService;

	/**
	 * 할 일 추가
	 */
	@PostMapping
	public ResponseEntity<Long> addTask(
			@RequestBody @Valid TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(personalTaskService.createTask(dto, userDetails.getMember().getId()));
	}

	/**
	 * 사용자의 모든 할 일 목록 조회
	 */
	@GetMapping
	public ResponseEntity<Page<TaskResponseDto>> getAllTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<TaskResponseDto> tasks = personalTaskService.getAllTasksByMemberId(userDetails.getMember().getId(),
				pageable);
		return ResponseEntity.ok(tasks);
	}

	/**
	 * 사용자의 할 일 상세 조회 (개별조회)
	 */
	@GetMapping("/{taskId}")
	public ResponseEntity<TaskResponseDto> getTaskById(
			@PathVariable Long taskId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(personalTaskService.getTaskById(taskId, userDetails.getMember().getId()));
	}

	/**
	 * 할 일 수정
	 */
	@PutMapping("/{targetId}")
	public ResponseEntity<Void> updateTask(
			@PathVariable Long targetId,
			@RequestBody @Valid TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		personalTaskService.updateTask(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	/**
	 * 할 일 완료 상태로 변경
	 */
	@PutMapping("/{targetId}/toggle")
	public ResponseEntity<Void> toggleTaskDone(
			@PathVariable Long targetId,
			@RequestBody @Valid ToggleRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		personalTaskService.toggleDone(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	/**
	 * 할 일 삭제
	 */
	@DeleteMapping("/{targetId}")
	public ResponseEntity<Void> deleteTask(
			@PathVariable Long targetId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		personalTaskService.deleteTask(targetId, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	/**
	 * 조건 별로 선택된 정렬 조회
	 */
	@GetMapping("/filter")
	public ResponseEntity<Page<TaskResponseDto>> filterTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(required = false) Long teamId,
			@RequestParam(required = false) LocalDate startDate,
			@RequestParam(required = false) LocalDate endDate,
			@RequestParam(required = false) String deadlineStr,
			@RequestParam(required = false) Boolean flag,
			@RequestParam(required = false) LocalDate createdAt,
			@RequestParam(required = false) Boolean done,
			@RequestParam(defaultValue = "0") int page
	) {

		LocalDateTime deadline = null;
		if (deadlineStr != null) {
			LocalDate deadlineDate = LocalDate.parse(deadlineStr);
			deadline = deadlineDate.atStartOfDay();
		}

		int size = 5;
		Pageable pageable = PageRequest.of(page, size);

		Page<TaskResponseDto> filteredTasks = personalTaskService.filterTask(
				userDetails.getMember().getId(), pageable, teamId,
				startDate, endDate, deadline, flag, createdAt, done);

		return ResponseEntity.ok(filteredTasks);
	}

	/**
	 *  개인 할 일 검색
	 */
	@GetMapping("/search")
	public ResponseEntity<Page<TaskResponseDto>> searchTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int page
	) {

		int size = 5;
		Pageable pageable = PageRequest.of(page, size);

		Page<TaskResponseDto> searchedTasks = personalTaskService.searchTask(
				userDetails.getMember().getId(), pageable, keyword);

		return ResponseEntity.ok(searchedTasks);
	}

}
