package org.example.dotoli.service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import org.example.dotoli.dto.task.TaskResponseDto;
import org.example.dotoli.domain.QTask;
import org.example.dotoli.domain.QTeam;
import org.example.dotoli.domain.QTeamMember;
import org.example.dotoli.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskSearchService {

	private final JPAQueryFactory queryFactory;

	public List<TaskResponseDto> searchTasksByContentOrTeam(Long memberId, String keyword) {
		QTask task = QTask.task;
		QTeam team = QTeam.team;
		QTeamMember teamMember = QTeamMember.teamMember;

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

		return tasks.stream()
				.map(taskEntity -> new TaskResponseDto(taskEntity.getId(), taskEntity.getContent(),
						taskEntity.isDone()))
				.collect(Collectors.toList());
	}

}
