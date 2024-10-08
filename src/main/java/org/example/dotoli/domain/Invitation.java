package org.example.dotoli.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팀 초대 정보를 표현하는 엔티티 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Invitation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member inviter;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member invitee;

	@Enumerated(EnumType.STRING)
	private InvitationStatus status;

	private LocalDateTime createdAt;

	private Invitation(Team team, Member inviter, Member invitee) {
		this.team = team;
		this.inviter = inviter;
		this.invitee = invitee;
		this.status = InvitationStatus.PENDING;
		this.createdAt = LocalDateTime.now(); // TODO Auditing 고려
	}

	public static Invitation createNew(Team team, Member inviter, Member invitee) {
		return new Invitation(team, inviter, invitee);
	}

	public void accept() {
		this.status = InvitationStatus.ACCEPTED;
	}

	public void reject() {
		this.status = InvitationStatus.REJECTED;
	}

}
