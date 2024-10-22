package org.example.dotoli.mapper;

import org.example.dotoli.domain.Task;
import org.example.dotoli.dto.task.TaskResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskMapper {

	public static TaskResponseDto toTaskResponseDto(Task task) {
		return new TaskResponseDto(
				task.getId(),
				task.getContent(),
				task.isDone(),
				task.getDeadline(),
				task.isFlag(),
				task.getCreatedAt()
		);
	}

}
