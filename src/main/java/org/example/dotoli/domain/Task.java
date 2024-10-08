package org.example.dotoli.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Task 항목을 표현하는 엔티티 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	private boolean done;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	private Team team;

	private Task(String content, Member member, Team team) {
		this.content = content;
		this.done = false;
		this.member = member;
		this.team = team;
	}

	public static Task createPersonalTask(String content, Member member) {
		return new Task(content, member, null);
	}

	public static Task createTeamTask(String content, Member member, Team team) {
		return new Task(content, member, team);
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateDone(boolean done) {
		this.done = done;
	}

}