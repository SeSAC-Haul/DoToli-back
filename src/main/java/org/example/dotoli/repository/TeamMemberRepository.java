package org.example.dotoli.repository;

import java.util.List;

import org.example.dotoli.domain.TeamMember;
import org.example.dotoli.dto.member.MemberResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 팀과 회원 간의 관계에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

	/**
	 * 회원의 팀 소속 여부 확인
	 */
	boolean existsByMemberIdAndTeamId(Long memberId, Long teamId);

	/**
	 * 팀 소속 회원 목록 조회
	 */
	@Query("SELECT new org.example.dotoli.dto.member.MemberResponseDto(m.id, m.email, m.nickname) " +
			"FROM TeamMember tm " +
			"JOIN tm.member m " +
			"WHERE tm.team.id = :teamId")
	List<MemberResponseDto> findMembersByTeamId(@Param("teamId") Long teamId);

}
