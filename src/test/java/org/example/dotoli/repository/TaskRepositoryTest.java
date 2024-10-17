package org.example.dotoli.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.example.dotoli.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/sql/task-repository-test-data.sql")
class TaskRepositoryTest {

	@Autowired
	private TaskRepository taskRepository;

	@Test
	public void findTeamTasks_는_특정_팀의_태스크만_반환한다() {
		// given
		Long teamId = 1L;

		// when
		List<Task> teamTasks = taskRepository.findTeamTasks(teamId);

		// then
		assertThat(teamTasks).hasSize(3);
		assertThat(teamTasks).isNotEmpty();
		assertThat(teamTasks).allMatch(task -> task.getTeam().getId().equals(teamId));
	}

	@Test
	public void findTeamTasks_는_완료되지_않은_태스크를_먼저_반환한다() {
		// given
		Long teamId = 1L;

		// when
		List<Task> teamTasks = taskRepository.findTeamTasks(teamId);

		// then
		assertThat(teamTasks).isNotEmpty();
		assertThat(teamTasks.get(0).isDone()).isFalse();
		assertThat(teamTasks.get(1).isDone()).isFalse();
		assertThat(teamTasks.get(2).isDone()).isTrue();
	}

	@Test
	public void findTeamTasks_는_생성일자_내림차순으로_정렬한다() {
		// given
		Long teamId = 1L;

		// when
		List<Task> teamTasks = taskRepository.findTeamTasks(teamId);

		// then
		assertThat(teamTasks).isNotEmpty();
		assertThat(teamTasks).isSortedAccordingTo((t1, t2) -> {
			if (t1.isDone() != t2.isDone()) {
				return Boolean.compare(t1.isDone(), t2.isDone());
			}
			return t2.getCreatedAt().compareTo(t1.getCreatedAt());
		});
	}


}
