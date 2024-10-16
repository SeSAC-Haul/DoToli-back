package org.example.dotoli.service;

import java.util.List;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.config.error.exception.TaskNotFoundException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Task;
import org.example.dotoli.dto.member.MyPageResponseDto;
import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TaskRepository;
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

	/**
	 * 간단한 할 일 추가
	 */
	@Override
	@Transactional
	public Long createSimpleTask(TaskRequestDto dto, Long currentMemberId) {
		Member member = memberRepository.getReferenceById(currentMemberId);
		Task task = Task.createSimplePersonalTask(dto.getContent(), member);
		return taskRepository.save(task).getId();
	}

	/**
	 * 상세한 할 일 추가
	 */
	@Override
	@Transactional
	public Long createDetailedTask(TaskRequestDto dto, Long currentMemberId) {
		Member member = memberRepository.getReferenceById(currentMemberId);
		Task task = Task.createDetailedPersonalTask(dto.getContent(), member, dto.getDeadline(), dto.isFlag());
		return taskRepository.save(task).getId();
	}

	/**
	 * 사용자의 모든 할 일 목록 조회
	 */
	public List<TaskResponseDto> getAllTasksByMemberId(Long currentMemberId) {
		List<Task> tasks = taskRepository.findTasksByMemberId(currentMemberId);
		return tasks.stream()
				.map(task -> new TaskResponseDto(
						task.getId(),
						task.getContent(),
						task.isDone(),
						task.getDeadline(),
						task.isFlag(),
						task.getCreatedAt()   // DTO로 변환하는 과정 직접 작성
				))
				.toList();
	}

	/**
	 * 할 일 상세 조회 (개별 할 일 조회)
	 */
	@Override
	public TaskResponseDto getTaskById(Long taskId) {
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
	 * 소유권 검증
	 */
	private void validateTaskOwnership(Long taskOwnerId, Long currentMemberId) {
		if (!taskOwnerId.equals(currentMemberId)) {
			throw new ForbiddenException("해당 항목을 수정할 권한이 없습니다.");
		}
	}

	public MyPageResponseDto getMyPageInfo(Long memberId) {
		Member member = memberRepository.getReferenceById(memberId);

		Long totalTasks = getTotalTaskCountForMember(memberId);
		Long completedTasks = getCompletedTaskCountForMember(memberId);
		Long completionRate = calculateCompletionRate(memberId);

		return new MyPageResponseDto(member.getEmail(), member.getNickname(), totalTasks, completedTasks,
				completionRate);

	}

	@Transactional(readOnly = true)
	public Long getTotalTaskCountForMember(Long memberId) {
		return taskRepository.countAllTasksByMemberId(memberId);
	}

	@Transactional(readOnly = true)
	public Long getCompletedTaskCountForMember(Long memberId) {
		return taskRepository.countCompletedTasksByMemberId(memberId);
	}

	public Long calculateCompletionRate(Long memberId) {
		long totalTasks = getTotalTaskCountForMember(memberId);
		long completedTasks = getCompletedTaskCountForMember(memberId);

		if (totalTasks == 0) {
			return 0L;
		}

		return (completedTasks * 100) / totalTasks;
	}

}
