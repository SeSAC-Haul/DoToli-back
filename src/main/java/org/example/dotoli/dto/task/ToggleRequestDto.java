package org.example.dotoli.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Task 항목 완료 상태 토글 요청 정보를 담는 DTO 클래스
 */
@Data
public class ToggleRequestDto {

	@NotNull
	private boolean done;

}
