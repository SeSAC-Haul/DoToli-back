package org.example.dotoli.service;

import java.util.List;

import org.example.dotoli.dto.task.TaskRequestDto;
import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.dto.task.ToggleRequestDto;

public interface TaskService {

	Long createSimpleTask(TaskRequestDto dto, Long memberId);

	Long createDetailedTask(TaskRequestDto dto, Long memberId);

	TaskResponseDto getTaskById(Long taskId);

	void updateTask(Long taskId, TaskRequestDto dto, Long memberId);

	void deleteTask(Long targetId, Long memberId);

	void toggleDone(Long targetId, ToggleRequestDto dto, Long member);

}
