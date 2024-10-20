package org.example.dotoli.dto.task;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Task 항목 응답 정보를 담는 DTO 클래스
 */
@Data
public class TaskResponseDto {

	private Long id;

	private String content;

	private boolean done;

	private LocalDateTime createdAt;

	private LocalDateTime deadline; // LocalDatetime -> LocalDateTime 수정

	private boolean flag;

	public TaskResponseDto(Long id, String content, boolean done, LocalDateTime deadline,
			boolean flag, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.done = done;
		this.deadline = deadline;
		this.flag = flag;
		this.createdAt = createdAt;
	}

}
