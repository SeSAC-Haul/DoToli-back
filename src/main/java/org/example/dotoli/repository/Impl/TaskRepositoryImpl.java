package org.example.dotoli.repository.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.dotoli.domain.QTask;
import org.example.dotoli.domain.Task;
import org.example.dotoli.repository.TaskRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

/**
 * QueryDSL을 사용하여 다양한 조건에 따라 할 일을 조건별로 선택하는 커스텀 리포지토리 구현 클래스입니다.
 */

@Repository
@Slf4j
public class TaskRepositoryImpl implements TaskRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public TaskRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Task> TaskFilter(
			Long memberId, Pageable pageable, Long teamId, LocalDate startDate,
			LocalDate endDate, LocalDateTime deadline,
			Boolean flag, LocalDate createdAt, Boolean done, String keyword) {

		QTask task = QTask.task;

		BooleanBuilder builder = new BooleanBuilder();

		int fixedPageSize = 5;
		Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), fixedPageSize);

		// 개인 할 일 페이지의 경우 팀 ID가 없는 경우
		if (teamId == null) {
			builder.and(task.member.id.eq(memberId)).and(task.team.isNull());
			// 기간(생성일 기준)
			if (startDate != null) {
				builder.and(task.createdAt.goe(startDate.atStartOfDay()));
			}
			if (endDate != null) {
				builder.and(task.createdAt.loe(endDate.atTime(23, 59, 59)));
			}
		} else {
			// 팀 할 일 페이지의 경우 해당 팀 ID
			builder.and(task.team.id.eq(teamId));
			if (startDate != null) {
				builder.and(task.createdAt.goe(startDate.atStartOfDay()));
			}
			if (endDate != null) {
				builder.and(task.createdAt.loe(endDate.atTime(23, 59, 59)));
			}
		}

		// 생성일
		if (createdAt != null) {
			builder.and(task.createdAt.eq(createdAt.atStartOfDay()));
		}

		// 중요도
		if (flag != null) {
			builder.and(task.flag.eq(flag));
		}

		// 완료 상태
		if (done != null) {
			builder.and(task.done.eq(done));
		}

		// 검색어 조건 추가 (2글자 이상)
		if (keyword != null && keyword.length() >= 2) {
			builder.and(task.content.containsIgnoreCase(keyword));
		}

		// 마감일
		if (deadline != null) {
			// deadline을 00:00:00으로 설정
			LocalDateTime standardDeadline = deadline.toLocalDate().atStartOfDay();
			builder.and(task.deadline.loe(standardDeadline));
		}

		// 페이징된 데이터 조회 (if문 밖으로 이동)
		List<Task> tasks = queryFactory
				.selectFrom(task)
				.where(builder)
				.orderBy(
						deadline != null
								? Expressions.stringTemplate(
								"ABS(TIMESTAMPDIFF(SECOND, {0}, {1}))",
								task.deadline,
								deadline.toLocalDate().atStartOfDay()
						).asc()
								: task.createdAt.desc()
				)
				.offset(fixedPageable.getOffset())
				.limit(fixedPageable.getPageSize())
				.fetch();

		// 총 데이터 개수 계산
		Long countResult = queryFactory
				.select(task.id.count())
				.from(task)
				.where(builder)
				.fetchOne();

		long total = countResult != null ? countResult : 0;

		return new PageImpl<>(tasks, fixedPageable, total);
	}

}
