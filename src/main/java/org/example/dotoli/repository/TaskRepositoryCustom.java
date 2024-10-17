package org.example.dotoli.repository;

import java.time.LocalDate;

import org.example.dotoli.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepositoryCustom {

	/**
	 * 사용자 정의 쿼리를 위한 Task 리포지토리 인터페이스
	 */
	Page<Task> TaskFilter(Long memberId, Pageable pageable,
			Long teamId,
			LocalDate startDate,
			LocalDate endDate,
			LocalDate deadline,
			Boolean flag,
			LocalDate createdAt,
			Boolean done);

}
