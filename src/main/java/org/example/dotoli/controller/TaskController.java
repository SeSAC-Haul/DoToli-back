package org.example.dotoli.controller;

import org.example.dotoli.dto.member.MyPageResponseDto;
import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.security.userdetails.CustomUserDetails;
import org.example.dotoli.service.TaskSearchService;
import org.example.dotoli.service.TaskService;
import org.springframework.data.domain.Page;
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

/**
 * Task 항목 관련 엔드포인트를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	private final TaskSearchService taskSearchService;

	@PostMapping
	public ResponseEntity<Long> addNewTask(
			@RequestBody @Valid TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(taskService.saveTask(dto, userDetails.getMember().getId()));
	}

	@GetMapping
	public ResponseEntity<Page<TaskResponseDto>> getAllTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam(defaultValue = "0") int page
	) {
		return ResponseEntity.ok(taskService.findAll(userDetails.getMember().getId(), page));
	}

	@PutMapping("/{targetId}")
	public ResponseEntity<Void> updateTask(
			@PathVariable Long targetId,
			@RequestBody @Valid TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		taskService.updateTask(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	@PutMapping("/{targetId}/toggle")
	public ResponseEntity<Void> toggleTaskDone(
			@PathVariable Long targetId,
			@RequestBody @Valid ToggleRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		taskService.toggleDone(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{targetId}")
	public ResponseEntity<Void> deleteTask(
			@PathVariable Long targetId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		taskService.deleteTask(targetId, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	@GetMapping("/mypage")
	public ResponseEntity<MyPageResponseDto> getMyPageInfo(
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		Long memberId = userDetails.getMember().getId();
		MyPageResponseDto dto = taskService.getMyPageInfo(memberId);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<TaskResponseDto>> searchTasks(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page) {
		Long memberId = userDetails.getMember().getId();
		Page<TaskResponseDto> tasks = taskSearchService.searchTasksByContentOrTeam(memberId, keyword, page);
		return ResponseEntity.ok(tasks);
	}

}

