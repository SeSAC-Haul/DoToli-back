package org.example.dotoli.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.example.dotoli.config.error.exception.DuplicateTeamNameException;
import org.example.dotoli.domain.Member;
import org.example.dotoli.domain.TeamMember;
import org.example.dotoli.dto.team.TeamRequestDto;
import org.example.dotoli.repository.MemberRepository;
import org.example.dotoli.repository.TeamMemberRepository;
import org.example.dotoli.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private TeamMemberRepository teamMemberRepository;

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private TeamService teamService;

	private Member member;

	private TeamRequestDto teamRequestDto;

	@BeforeEach
	void setUp() {
		member = Member.createNew("member@test.com", "test1234", "TestNickname");
		teamRequestDto = new TeamRequestDto("New Team");
	}

	@Test
	@DisplayName("팀 생성 성공 테스트")
	void createTeam_ShouldCreateTeamSuccessfully() {
		// Given
		Long memberId = 1L;
		Long expectedTeamMemberId = 1L;
		when(teamRepository.existsByTeamName(any(String.class))).thenReturn(false);
		when(memberRepository.getReferenceById(any(Long.class))).thenReturn(member);

		when(teamMemberRepository.save(any(TeamMember.class))).thenAnswer(invocation -> {
			TeamMember savedTeamMember = invocation.getArgument(0);
			setId(savedTeamMember, expectedTeamMemberId);
			return savedTeamMember;
		});

		// When
		Long teamMemberId = teamService.createTeam(memberId, teamRequestDto);

		// Then
		assertEquals(expectedTeamMemberId, teamMemberId);
		verify(teamRepository).existsByTeamName("New Team");
		verify(memberRepository).getReferenceById(memberId);
		verify(teamMemberRepository).save(any(TeamMember.class));
	}

	@Test
	@DisplayName("중복된 팀 이름으로 생성 시 예외 발생 테스트")
	void createTeam_ShouldThrowDuplicateTeamNameException_WhenTeamNameExists() {
		// Given
		Long memberId = 1L;
		when(teamRepository.existsByTeamName(any(String.class))).thenReturn(true);

		// When & Then
		DuplicateTeamNameException exception = assertThrows(DuplicateTeamNameException.class, () -> {
			teamService.createTeam(memberId, teamRequestDto);
		});

		// Then
		assertEquals("이미 사용 중인 팀 이름입니다.", exception.getMessage());
		verify(teamRepository).existsByTeamName("New Team");
		verify(memberRepository, never()).getReferenceById(any(Long.class));
		verify(teamMemberRepository, never()).save(any(TeamMember.class));
	}

	// TeamMember에 ID를 설정하는 헬퍼 메서드
	private void setId(TeamMember teamMember, Long id) {
		try {
			Field idField = TeamMember.class.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(teamMember, id);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("Failed to set TeamMember ID", e);
		}
	}

}