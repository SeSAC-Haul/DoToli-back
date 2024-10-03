package org.example.dotoli.dto.task;

import lombok.Data;

/**
 * Task 항목 응답 정보를 담는 DTO 클래스
 */
@Data
public class TaskResponseDto {

	private Long id;

	private String content;

	private boolean done;

	public TaskResponseDto(Long id, String content, boolean done) {
		this.id = id;
		this.content = content;
		this.done = done;
	}

}
