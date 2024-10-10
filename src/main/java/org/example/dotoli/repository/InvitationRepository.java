package org.example.dotoli.repository;

import org.example.dotoli.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 초대 정보에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
