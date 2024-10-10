package org.example.dotoli.service;

import java.util.List;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.config.error.exception.TaskNotFoundException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Task;
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
public class TaskService {

    private final TaskRepository taskRepository;

    private final MemberRepository memberRepository;

    /**
     * 할 일 추가
     */
    @Transactional
    public Long saveTask(TaskRequestDto dto, Long currentMemberId) {
        Member member = memberRepository.getReferenceById(currentMemberId);
        Task task = Task.createPersonalTask(dto.getContent(), member);

        return taskRepository.save(task).getId();
    }

    /**
     * 할 일 목록 조회
     */
    public List<TaskResponseDto> findAll(Long currentMemberId) {
        return taskRepository.findAllSorted(currentMemberId).stream()
                .map(task -> new TaskResponseDto(task.getId(), task.getContent(), task.isDone()))
                .toList();
    }

    /**
     * 할 일 수정
     */
    @Transactional
    public void updateTask(Long targetId, TaskRequestDto dto, Long currentMemberId) {
        Task task = findTaskAndValidateOwnership(targetId, currentMemberId);

        task.updateContent(dto.getContent());
    }

    /**
     * 할 일 삭제
     */
    @Transactional
    public void deleteTask(Long targetId, Long currentMemberId) {
        Task task = findTaskAndValidateOwnership(targetId, currentMemberId);

        taskRepository.delete(task);
    }

    /**
     * 할 일 완료 상태 변경
     */
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

    @Transactional(readOnly = true)
    public Long getTotalTaskCountForMember(Long memberId) {
        return taskRepository.countAllTasksByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public Long getCompletedTaskCountForMember(Long memberId) {
        return taskRepository.countCompletedTasksByMemberId(memberId);
    }

}
