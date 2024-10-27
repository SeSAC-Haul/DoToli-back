package org.example.dotoli.service;

import java.util.List;

import org.example.dotoli.dto.invitation.PendingInvitationDto;
import org.example.dotoli.dto.member.MyPageResponseDto;
import org.example.dotoli.repository.InvitationRepository;
import org.example.dotoli.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 마이페이지 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

	private final TaskRepository taskRepository;

	private final InvitationRepository invitationRepository;

	/**
	 * 사용자 ID에 해당하는 마이페이지 정보를 MyPageResponseDto에 담아 반환
	 */
	public MyPageResponseDto getMyPageInfo(Long memberId) {
		Long totalTasksCount = taskRepository.countAllTasksByMemberId(memberId);
		Long completedTasksCount = taskRepository.countCompletedTasksByMemberId(memberId);
		Long completionRate = calculateCompletionRate(totalTasksCount, completedTasksCount);
		List<PendingInvitationDto> pendingInvitationDtos
				= invitationRepository.findPendingInvitationsByInviteeId(memberId);

		return new MyPageResponseDto(totalTasksCount, completedTasksCount, completionRate, pendingInvitationDtos);
	}

	/**
	 * 달성률 계산
	 */
	private Long calculateCompletionRate(Long totalTasksCount, Long completedTasksCount) {
		if (totalTasksCount == 0) {
			return 0L;
		}

		return (completedTasksCount * 100) / totalTasksCount;
	}

}
