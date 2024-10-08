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
 * 팀과 회원 간의 다대다 관계를 표현하는 엔티티 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TeamMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	private Team team;

	private TeamMember(Member member, Team team) {
		this.member = member;
		this.team = team;
	}

	public static TeamMember createNew(Member member, Team team) {
		return new TeamMember(member, team);
	}

}
