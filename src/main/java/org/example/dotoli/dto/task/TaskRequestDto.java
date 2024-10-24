package org.example.dotoli.dto.task;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Task 항목 생성/수정 요청 정보를 담는 DTO 클래스
 */
@Data
public class TaskRequestDto {

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime deadline;

	private boolean flag;

}
