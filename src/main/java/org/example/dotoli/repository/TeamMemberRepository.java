package org.example.dotoli.repository;

import org.example.dotoli.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 팀과 회원 간의 관계에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

	boolean existsByMemberIdAndTeamId(Long memberId, Long teamId);

}
