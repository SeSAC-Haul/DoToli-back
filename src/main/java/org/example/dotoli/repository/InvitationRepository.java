package org.example.dotoli.repository;

import java.util.List;

import org.example.dotoli.domain.Invitation;
import org.example.dotoli.domain.InvitationStatus;
import org.example.dotoli.dto.invitation.PendingInvitationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 초대 정보에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

	/**
	 * 초대 수신 목록
	 */
	@Query("SELECT new org.example.dotoli.dto.invitation.PendingInvitationDto " +
			"(i.id, t.id, t.teamName, m.nickname, m.email) " +
			"FROM Invitation i " +
			"JOIN Team t ON i.team.id = t.id " +
			"JOIN Member m ON i.inviter.id = m.id " +
			"WHERE i.invitee.id = :inviteeId " +
			"AND i.status = 'PENDING'")
	List<PendingInvitationDto> findPendingInvitationsByInviteeId(@Param("inviteeId") Long inviteeId);

	boolean existsByTeamIdAndInviteeIdAndStatus(Long teamId, Long memberId, InvitationStatus status);

}
