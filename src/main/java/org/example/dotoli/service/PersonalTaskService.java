package org.example.dotoli.service;

import java.time.LocalDate;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.config.error.exception.TaskNotFoundException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Task;
import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TaskRepository;
import org.example.dotoli.repository.TaskRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * Task 항목 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalTaskService implements TaskService {

	private final TaskRepository taskRepository;

	private final MemberRepository memberRepository;

	private final TaskRepositoryCustom taskRepositoryCustom;

	/**
	 * 할 일 추가
	 */
	@Override
	@Transactional
	public Long createTask(TaskRequestDto dto, Long currentMemberId) {
		Member member = memberRepository.getReferenceById(currentMemberId);
		Task task = Task.createPersonalTask(dto.getContent(), member, dto.getDeadline(), dto.isFlag());
		return taskRepository.save(task).getId();
	}

	/**
	 * 사용자의 모든 할 일 목록 조회
	 */
	public Page<TaskResponseDto> getAllTasksByMemberId(Long memberId, Pageable pageable) {
		Page<Task> tasks = taskRepository.findTasksByMemberId(memberId, pageable);
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
	 * 할 일 상세 조회 (개별 할 일 조회)
	 */
	@Override
	public TaskResponseDto getTaskById(Long taskId, Long memberId) {
		// FIXME 검증 로직 추가 필요 
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));
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
	public void updateTask(Long taskId, TaskRequestDto dto, Long currentMemberId) {
		Task task = findTaskAndValidateOwnership(taskId, currentMemberId);

		task.updateContent(dto.getContent());
		task.updateDeadline(dto.getDeadline());
		task.updateFlag(dto.isFlag());
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

	/**
	 * 특정 할 일 조회 및 소유권 확인
	 */
	private Task findTaskAndValidateOwnership(Long taskId, Long currentMemberId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(TaskNotFoundException::new);

		validateTaskOwnership(task.getMember().getId(), currentMemberId);

		return task;
	}

	/**
	 * 조건 별로 선택된 정렬 조회
	 */
	public Page<TaskResponseDto> filterTasks(
			Long memberId, Pageable pageable, Long teamId,
			LocalDate startDate, LocalDate endDate,
			LocalDate deadline, Boolean flag,
			LocalDate createdAt, Boolean done) {
		Page<Task> tasks = taskRepositoryCustom.TaskFilter(
				memberId, pageable, teamId, startDate,
				endDate, deadline, flag, createdAt, done);
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
	 * 소유권 검증
	 */
	private void validateTaskOwnership(Long taskOwnerId, Long currentMemberId) {
		if (!taskOwnerId.equals(currentMemberId)) {
			throw new ForbiddenException("해당 항목을 수정할 권한이 없습니다.");
		}
	}

}
