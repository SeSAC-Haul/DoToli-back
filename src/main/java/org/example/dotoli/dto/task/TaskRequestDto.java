package org.example.dotoli.dto.task;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Task 항목 생성/수정 요청 정보를 담는 DTO 클래스
 */
@Data
@Builder
public class TaskRequestDto {

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;

	private LocalDate deadline;

	private boolean flag;

	@NotNull(message = "팀 ID는 필수 입력값입니다.", groups = TeamTaskValidation.class)
	private Long teamId;

}
