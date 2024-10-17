package org.example.dotoli.service;

import java.util.List;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.config.error.exception.TaskNotFoundException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Task;
import org.example.dotoli.domain.Team;
import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TaskRepository;
import org.example.dotoli.repository.TeamMemberRepository;
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

	private final TeamMemberRepository teamMemberRepository;

	/**
	 * 간단한 할 일 추가
	 */
	@Override
	@Transactional
	public Long createSimpleTask(TaskRequestDto dto, Long memberId) {
		Long teamId = dto.getTeamId();

		validateMemberTeamAccess(memberId, teamId);

		Member member = memberRepository.getReferenceById(memberId);
		Team team = teamRepository.getReferenceById(teamId);

		Task task = Task.createSimpleTeamTask(dto.getContent(), member, team);

		return taskRepository.save(task).getId();
	}

	/**
	 * 상세한 할 일 추가
	 */
	@Override
	@Transactional
	public Long createDetailedTask(TaskRequestDto dto, Long memberId) {
		Long teamId = dto.getTeamId();

		validateMemberTeamAccess(memberId, teamId);

		Member member = memberRepository.getReferenceById(memberId);
		Team team = teamRepository.getReferenceById(teamId);

		Task task = Task.createDetailedTeamTask(dto.getContent(), member, dto.getDeadline(), dto.isFlag(), team);

		return taskRepository.save(task).getId();
	}

	/**
	 * 할 일 목록 조회
	 */
	public List<TaskResponseDto> getAllTasksByTeamId(Long memberId, Long teamId) {
		validateMemberTeamAccess(memberId, teamId);

		return taskRepository.findTeamTasks(teamId).stream()
				.map(task -> new TaskResponseDto(task.getId(), task.getContent(), task.isDone(), task.getDeadline(),
						task.isFlag(), task.getCreatedAt()))
				.toList();
	}

	/**
	 * 할 일 상세 조회 (개별 할 일 조회)
	 * FIXME 검증 로직 추가 필요
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
	public void updateTask(Long targetId, TaskRequestDto dto, Long memberId) {
		Long teamId = dto.getTeamId();

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
	@Override
	@Transactional
	public void deleteTask(Long targetId, Long memberId) {
		Task task = taskRepository.findById(targetId)
				.orElseThrow(TaskNotFoundException::new);

		Long teamId = task.getTeam().getId();
		validateMemberTeamAccess(memberId, teamId);

		taskRepository.delete(task);
	}

	/**
	 * 할 일 완료 상태 변경
	 */
	@Override
	@Transactional
	public void toggleDone(Long targetId, ToggleRequestDto dto, Long memberId) {
		Task task = taskRepository.findById(targetId)
				.orElseThrow(TaskNotFoundException::new);

		Long teamId = task.getTeam().getId();
		validateMemberTeamAccess(memberId, teamId);

		task.updateDone(dto.isDone());
	}

	private void validateMemberTeamAccess(Long memberId, Long teamId) {
		if (!teamMemberRepository.existsByMemberIdAndTeamId(memberId, teamId)) {
			throw new ForbiddenException("이 팀에 접근할 권한이 없습니다.");
		}
	}

}
