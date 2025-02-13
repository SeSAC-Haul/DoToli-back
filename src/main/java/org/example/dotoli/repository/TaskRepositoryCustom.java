package org.example.dotoli.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.example.dotoli.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 사용자 정의 쿼리를 위한 Task 리포지토리 인터페이스
 */
public interface TaskRepositoryCustom {

	Page<Task> TaskFilter(
			Long memberId,
			Pageable pageable,
			Long teamId,
			LocalDate startDate,
			LocalDate endDate,
			LocalDateTime deadline,
			Boolean flag,
			LocalDate createdAt,
			Boolean done,
			String keyword);

}
