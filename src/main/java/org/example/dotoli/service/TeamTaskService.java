package org.example.dotoli.service;

import java.util.List;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.config.error.exception.TaskNotFoundException;
import org.example.dotoli.config.error.exception.TeamNotFoundException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Task;
import org.example.dotoli.domain.Team;
import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TaskRepository;
import org.example.dotoli.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 팀 Task 항목 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamTaskService implements TaskService {

	private final TaskRepository taskRepository;

	private final MemberRepository memberRepository;

	private final TeamRepository teamRepository;

	/**
	 * 간단한 할 일 추가
	 */
	@Override
	@Transactional
	public Long createSimpleTask(TaskRequestDto dto, Long currentMemberId) {
		Member member = memberRepository.getReferenceById(currentMemberId);
		Team team = teamRepository.findById(dto.getTeamId())
				.orElseThrow(TeamNotFoundException::new);

		Task task = Task.createTeamTask(dto.getContent(), member, team);

		return taskRepository.save(task).getId();
	}

	/**
	 * 상세한 할 일 추가
	 */
	@Override
	@Transactional
	public Long createDetailedTask(TaskRequestDto dto, Long currentMemberId) {
		Member member = memberRepository.getReferenceById(currentMemberId);

		Task task = Task.createDetailedTask(dto.getContent(), member, dto.getDeadline(), dto.isFlag());

		return taskRepository.save(task).getId();
	}

	/**
	 * 할 일 목록 조회
	 */
	public List<TaskResponseDto> getAllTasksByMemberId(Long currentMemberId, Long teamId) {
		return taskRepository.findTeamTasks(currentMemberId, teamId).stream()
				.map(task -> new TaskResponseDto(task.getId(), task.getContent(), task.isDone(), task.getDeadline(),
						task.isFlag(), task.getCreatedAt()))
				.toList();
	}

	/**
	 * 할 일 상세 조회 (개별 할 일 조회)
	 */
	@Override
	public TaskResponseDto getTaskById(Long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(TaskNotFoundException::new);

		return new TaskResponseDto(
				task.getId(),
				task.getContent(),
				task.isDone(),
				task.getDeadline(),
				task.isFlag(),
				task.getCreatedAt()
		);
	}

	/**
	 * 할 일 수정
	 */
	@Override
	@Transactional
	public void updateTask(Long targetId, TaskRequestDto dto, Long currentMemberId) {
		Task task = findTaskAndValidateOwnership(targetId, currentMemberId);

		task.updateContent(dto.getContent());
	}

	/**
	 * 할 일 삭제
	 */
	@Override
	@Transactional
	public void deleteTask(Long targetId, Long currentMemberId) {
		Task task = findTaskAndValidateOwnership(targetId, currentMemberId);

		taskRepository.delete(task);
	}

	/**
	 * 할 일 완료 상태 변경
	 */
	@Override
	@Transactional
	public void toggleDone(Long targetId, ToggleRequestDto dto, Long currentMemberId) {
		Task task = findTaskAndValidateOwnership(targetId, currentMemberId);

		task.updateDone(dto.isDone());
	}

	private Task findTaskAndValidateOwnership(Long taskId, Long currentMemberId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(TaskNotFoundException::new);

		validateTaskOwnership(task.getMember().getId(), currentMemberId);

		return task;
	}

	private void validateTaskOwnership(Long taskOwnerId, Long currentMemberId) {
		if (!taskOwnerId.equals(currentMemberId)) {
			throw new ForbiddenException("해당 항목을 수정할 권한이 없습니다.");
		}
	}

}
