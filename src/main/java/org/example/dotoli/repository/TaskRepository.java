package org.example.dotoli.repository;

import java.util.List;

import org.example.dotoli.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Task 항목에 대한 데이터베이스 작업을 처리하는 래포지토리 인터페이스
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
	@Query("SELECT t " +
			"FROM Task t " +
			"WHERE t.member.id = :memberId " +
			"ORDER BY t.done ASC, t.id DESC")
	Page<Task> findAllSorted(@Param("memberId") Long memberId, Pageable pageable);

	@Query("SELECT t " +
			"FROM Task t " +
			"WHERE t.member.id = :memberId AND t.content LIKE %:content%"
	)
	List<Task> findByContentContainingAndMemberId(@Param("memberId") Long memberId,
			@Param("content") String content);

	@Query("SELECT COUNT(t) " +
			"FROM Task t " +
			"WHERE t.member.id = :memberId")
	Long countAllTasksByMemberId(@Param("memberId") Long memberId);

	@Query("SELECT COUNT(t) " +
			"FROM Task t " +
			"WHERE t.member.id = :memberId AND t.done = true")
	Long countCompletedTasksByMemberId(@Param("memberId") Long memberId);

}
