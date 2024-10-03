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

	private Task(String content, boolean done, Member member) {
		this.content = content;
		this.done = done;
		this.member = member;
	}

	public static Task createNew(String content, Member member) {
		return new Task(content, false, member);
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateDone(boolean done) {
		this.done = done;
	}

}