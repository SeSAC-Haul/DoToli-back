package org.example.dotoli.service;

import org.example.dotoli.config.error.exception.ForbiddenException;
import org.example.dotoli.domain.Invitation;
import org.example.dotoli.domain.InvitationStatus;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.Team;
import org.example.dotoli.dto.invitation.InvitationRequestDto;
import org.example.dotoli.repository.InvitationRepository;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TeamMemberRepository;
import org.example.dotoli.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationService {

	private final InvitationRepository invitationRepository;

	private final TeamMemberRepository teamMemberRepository;

	private final MemberRepository memberRepository;

	private final TeamRepository teamRepository;

	/**
	 * 초대 생성
	 */
	@Transactional
	public Long createInvitation(InvitationRequestDto dto, Long inviterId, Long teamId) {
		Long inviteeId = dto.getInviteeId();

		validateInvitation(inviterId, teamId, inviteeId);

		Member invitee = memberRepository.getReferenceById(inviteeId);
		Member inviter = memberRepository.getReferenceById(inviterId);
		Team team = teamRepository.getReferenceById(teamId);

		Invitation invitation = Invitation.createNew(team, inviter, invitee);

		return invitationRepository.save(invitation).getId();
	}

	private void validateInvitation(Long inviterId, Long teamId, Long inviteeId) {
		validateMemberTeamAccess(inviterId, teamId);
		validateInviteeNotInTeam(teamId, inviteeId);
		validateNoPendingInvitation(teamId, inviteeId);
	}

	private void validateMemberTeamAccess(Long memberId, Long teamId) {
		if (!teamMemberRepository.existsByMemberIdAndTeamId(memberId, teamId)) {
			throw new ForbiddenException("이 팀에 접근할 권한이 없습니다.");
		}
	}

	private void validateInviteeNotInTeam(Long teamId, Long inviteeId) {
		if (teamMemberRepository.existsByMemberIdAndTeamId(inviteeId, teamId)) {
			throw new IllegalArgumentException();
		}
	}

	private void validateNoPendingInvitation(Long teamId, Long inviteeId) {
		if (invitationRepository.existsByTeamIdAndInviteeIdAndStatus(teamId, inviteeId, InvitationStatus.PENDING)) {
			throw new IllegalArgumentException();
		}
	}

}
