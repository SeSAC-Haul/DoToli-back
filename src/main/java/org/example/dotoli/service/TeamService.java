package org.example.dotoli.service;

import org.example.dotoli.config.error.exception.DuplicateTeamNameException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Team;
import org.example.dotoli.domain.TeamMember;
import org.example.dotoli.dto.team.TeamRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TeamMemberRepository;
import org.example.dotoli.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 팀 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;

	private final TeamMemberRepository teamMemberRepository;

	private final MemberRepository memberRepository;

	/**
	 * 팀 생성
	 */
	@Transactional
	public Long createTeam(Long memberId, TeamRequestDto dto) {
		checkDuplicateTeamName(dto.getTeamName());

		// Member 엔티티의 존재가 확실하므로 getReferenceById 메서드 사용
		Member member = memberRepository.getReferenceById(memberId);

		Team team = Team.createNew(dto.getTeamName());
		teamRepository.save(team);

		TeamMember teamMember = TeamMember.createNew(member, team);
		teamMemberRepository.save(teamMember);

		return team.getId();
	}

	/**
	 * 팀 이름 중복 체크
	 */
	private void checkDuplicateTeamName(String teamName) {
		if (teamRepository.existsByTeamName(teamName)) {
			throw new DuplicateTeamNameException();
		}
	}

}
