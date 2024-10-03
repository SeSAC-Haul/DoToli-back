package org.example.dotoli.repository;

import java.util.Optional;

import org.example.dotoli.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 정보에 대한 데이터베이스 작업을 처리하는 레포지토리 인터페이스
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

}
