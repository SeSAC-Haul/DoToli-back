package org.example.dotoli.service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.domain.QTask;
import org.example.dotoli.domain.QTeam;
import org.example.dotoli.domain.QTeamMember;
import org.example.dotoli.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskSearchService {

	private final JPAQueryFactory queryFactory;

	public Page<TaskResponseDto> searchTasksByContentOrTeam(Long memberId, String keyword, int page) {
		QTask task = QTask.task;
		QTeam team = QTeam.team;
		QTeamMember teamMember = QTeamMember.teamMember;

		Pageable pageable = PageRequest.of(page, 5);

		/**
		 * content로 검색하거나, 팀 이름으로 검색 (현재 사용자 또는 같은 팀에 한정)
		 */
		List<Task> tasks = queryFactory.selectFrom(task)
				.leftJoin(task.team, team).fetchJoin()
				.leftJoin(teamMember).on(teamMember.team.eq(team))
				.where(
						task.content.containsIgnoreCase(keyword).and(task.member.id.eq(memberId))
								.or(team.teamName.containsIgnoreCase(keyword).and(task.team.id.in(
										queryFactory.select(teamMember.team.id)
												.from(teamMember)
												.where(teamMember.member.id.eq(memberId))
								)))
				)
				.fetch();

		int start = (int)pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), tasks.size());
		List<TaskResponseDto> taskDtos = tasks.subList(start, end).stream()
				.map(taskEntity -> new TaskResponseDto(taskEntity.getId(), taskEntity.getContent(),
						taskEntity.isDone()))
				.collect(Collectors.toList());

		return new PageImpl<>(taskDtos, pageable, tasks.size());
	}

}
