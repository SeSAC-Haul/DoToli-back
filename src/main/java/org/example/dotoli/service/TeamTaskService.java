package org.example.dotoli.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.config.error.exception.TaskNotFoundException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Task;
import org.example.dotoli.domain.Team;
import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.mapper.TaskMapper;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TaskRepository;
import org.example.dotoli.repository.TaskRepositoryCustom;
import org.example.dotoli.repository.TeamMemberRepository;
import org.example.dotoli.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 팀 Task 항목 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamTaskService {

	private final TaskRepository taskRepository;

	private final MemberRepository memberRepository;

	private final TeamRepository teamRepository;

	private final TeamMemberRepository teamMemberRepository;

	private final TaskRepositoryCustom taskRepositoryCustom;

	/**
	 * 할 일 추가
	 */
	@Transactional
	public Long createTask(TaskRequestDto dto, Long memberId, Long teamId) {
		validateMemberTeamAccess(memberId, teamId);

		Member member = memberRepository.getReferenceById(memberId);
		Team team = teamRepository.getReferenceById(teamId);

		Task task = Task.createTeamTask(dto.getContent(), member, dto.getDeadline(), dto.isFlag(), team);

		return taskRepository.save(task).getId();
	}

	/**
	 * 할 일 목록 조회
	 */
	public Page<TaskResponseDto> getAllTasksByTeamId(Long memberId, Long teamId, Pageable pageable) {
		validateMemberTeamAccess(memberId, teamId);

		// return taskRepository.findTeamTasks(teamId, pageable).stream()
		// 		.map(TaskMapper::toTaskResponseDto)
		// 		.toList();
		return taskRepository.findTeamTasks(teamId, pageable).map(
				task -> new TaskResponseDto(task.getId(), task.getContent(), task.isDone(), task.getDeadline(),
						task.isFlag(), task.getCreatedAt())
		);
	}

	/**
	 * 할 일 상세 조회 (개별 할 일 조회)
	 */
	public TaskResponseDto getTaskById(Long taskId, Long memberId, Long teamId) {
		validateMemberTeamAccess(memberId, teamId);

		Task task = taskRepository.findById(taskId)
				.orElseThrow(TaskNotFoundException::new);

		return TaskMapper.toTaskResponseDto(task);
	}

	/**
	 * 할 일 수정
	 */
	@Transactional
	public void updateTask(Long targetId, TaskRequestDto dto, Long memberId, Long teamId) {
		validateMemberTeamAccess(memberId, teamId);

		Task task = taskRepository.findById(targetId)
				.orElseThrow(TaskNotFoundException::new);

		task.updateContent(dto.getContent());
		task.updateDeadline(dto.getDeadline());
		task.updateFlag(dto.isFlag());
	}

	/**
	 * 할 일 삭제
	 */
	@Transactional
	public void deleteTask(Long targetId, Long memberId, Long teamId) {
		validateMemberTeamAccess(memberId, teamId);

		Task task = taskRepository.findById(targetId)
				.orElseThrow(TaskNotFoundException::new);

		taskRepository.delete(task);
	}

	/**
	 * 할 일 완료 상태 변경
	 */
	@Transactional
	public void toggleDone(Long targetId, ToggleRequestDto dto, Long memberId, Long teamId) {
		validateMemberTeamAccess(memberId, teamId);

		Task task = taskRepository.findById(targetId)
				.orElseThrow(TaskNotFoundException::new);

		task.updateDone(dto.isDone());
	}

	private void validateMemberTeamAccess(Long memberId, Long teamId) {
		if (!teamMemberRepository.existsByMemberIdAndTeamId(memberId, teamId)) {
			throw new ForbiddenException("이 팀에 접근할 권한이 없습니다.");
		}
	}

	/**
	 * 팀 할 일 조건 별로 선택된 정렬 조회
	 */
	public Page<TaskResponseDto> filterTask(Long memberId, Pageable pageable, Long teamId,
			LocalDate startDate, LocalDate endDate, LocalDateTime deadline,
			Boolean flag, LocalDate createdAt, Boolean done, String keyword) {
		if (teamId != null) {
			validateMemberTeamAccess(memberId, teamId);
		}

		Page<Task> tasks = taskRepositoryCustom.TaskFilter(
				memberId, pageable, teamId, startDate, endDate, deadline, flag, createdAt, done, keyword);

		return tasks.map(task -> new TaskResponseDto(
				task.getId(),
				task.getContent(),
				task.isDone(),
				task.getDeadline(),
				task.isFlag(),
				task.getCreatedAt()
		));
	}

	/**
	 *  팀 할 일 검색
	 */
	public Page<TaskResponseDto> searchTask(Long memberId, Long teamId, Pageable pageable, String keyword) {
		if (teamId != null) {
			validateMemberTeamAccess(memberId, teamId);
		}

		Page<Task> tasks = taskRepositoryCustom.TaskFilter(
				memberId, pageable, teamId, null, null, null, null, null, null, keyword);

		return tasks.map(task -> new TaskResponseDto(
				task.getId(),
				task.getContent(),
				task.isDone(),
				task.getDeadline(),
				task.isFlag(),
				task.getCreatedAt()
		));
	}

}
