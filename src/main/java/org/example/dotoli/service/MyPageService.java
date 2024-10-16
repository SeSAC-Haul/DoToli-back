package org.example.dotoli.service;

import org.example.dotoli.domain.Member;
import org.example.dotoli.dto.member.MyPageResponseDto;
import org.example.dotoli.repository.MemberRepository;
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

	private final MemberRepository memberRepository;

	/**
	 * 사용자 ID에 해당하는 마이페이지 정보를 MyPageResponseDto에 담아 반환
	 */
	public MyPageResponseDto getMyPageInfo(Long memberId) {
		Member member = memberRepository.getReferenceById(memberId);

		Long totalTasks = getTotalTaskCountForMember(memberId);
		Long completedTasks = getCompletedTaskCountForMember(memberId);
		Long completionRate = calculateCompletionRate(memberId);

		return new MyPageResponseDto(member.getEmail(), member.getNickname(), totalTasks, completedTasks,
				completionRate);

	}

	/**
	 * 사용자의 모든 할 일 개수 조회
	 */
	public Long getTotalTaskCountForMember(Long memberId) {
		return taskRepository.countAllTasksByMemberId(memberId);
	}

	/**
	 * 사용자의 완료한 할 일 개수 조회
	 */
	public Long getCompletedTaskCountForMember(Long memberId) {
		return taskRepository.countCompletedTasksByMemberId(memberId);
	}

	/**
	 * 달성률 계산
	 */
	private Long calculateCompletionRate(Long memberId) {
		long totalTasks = getTotalTaskCountForMember(memberId);
		long completedTasks = getCompletedTaskCountForMember(memberId);

		if (totalTasks == 0) {
			return 0L;
		}

		return (completedTasks * 100) / totalTasks;
	}

}
