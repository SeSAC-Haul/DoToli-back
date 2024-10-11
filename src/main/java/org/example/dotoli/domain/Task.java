package org.example.dotoli.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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

	// 생성일
	private LocalDateTime createdAt;

	// 마감일
	private LocalDate deadline;

	// 중요도(플래그)
	private boolean flag;

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

	// 간단한 할 일 생성 메소드
	public static Task createSimpleTask(String content, Member member) {
		Task task = new Task(content, member, null);
		task.deadline = null;
		task.flag = false;
		return task;
	}

	// 상세 할 일 생성 메소드
	public static Task createDetailedTask(String content, Member member, LocalDate deadline, boolean flag) {
		Task task = new Task(content, member, null);
		task.deadline = deadline;
		task.flag = flag;
		return task;
	}

	public static Task createTeamTask(String content, Member member, Team team) {
		return new Task(content, member, team);
	}

	// 엔티티가 저장되기 전에 자동으로 생성일 지정
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateDone(boolean done) {
		this.done = done;
	}

	// 데드라인 적용 메소드
	public void updateDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	// 중요도(플래그) 적용 메소드
	public void updateFlag(boolean flag) {
		this.flag = flag;
	}

}