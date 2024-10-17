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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamTaskController {

	private final TeamTaskService teamTaskService;

	@PostMapping("/tasks")
	public ResponseEntity<Long> addNewTask(
			@RequestBody @Validated(TeamTaskValidation.class) TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		return ResponseEntity.ok(teamTaskService.createSimpleTask(dto, userDetails.getMember().getId()));
	}

	@GetMapping("/{teamId}/tasks")
	public ResponseEntity<List<TaskResponseDto>> getAllTask(
			@AuthenticationPrincipal CustomUserDetails userDetails,
			@PathVariable Long teamId
	) {
		return ResponseEntity.ok(teamTaskService.getAllTasksByTeamId(userDetails.getMember().getId(), teamId));
	}

	@PutMapping("/tasks/{targetId}")
	public ResponseEntity<Void> updateTask(
			@PathVariable Long targetId,
			@RequestBody @Validated(TeamTaskValidation.class) TaskRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.updateTask(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	@PutMapping("/tasks/{targetId}/toggle")
	public ResponseEntity<Void> toggleTaskDone(
			@PathVariable Long targetId,
			@RequestBody @Valid ToggleRequestDto dto,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.toggleDone(targetId, dto, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/tasks/{targetId}")
	public ResponseEntity<Void> deleteTask(
			@PathVariable Long targetId,
			@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		teamTaskService.deleteTask(targetId, userDetails.getMember().getId());

		return ResponseEntity.ok().build();
	}

}
