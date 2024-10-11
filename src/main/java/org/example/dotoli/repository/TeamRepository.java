package org.example.dotoli.repository;

import java.util.List;

import org.example.dotoli.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 팀 정보에 대한 데이터베이스 작업을 처리하는 래포지토리 인터페이스
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

	boolean existsByTeamName(String teamName);

	@Query("SELECT t "
			+ "FROM Team t JOIN TeamMember tm "
			+ "WHERE tm.member.id = :memberId")
	List<Team> findAllCurrentMemberTeam(Long memberId);

}
