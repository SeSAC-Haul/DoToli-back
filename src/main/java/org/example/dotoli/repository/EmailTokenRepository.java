package org.example.dotoli.repository;

import java.util.List;
import java.util.Optional;

import org.example.dotoli.domain.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

	List<EmailToken> findAllByEmail(String email);

	Optional<Object> findByToken(String token);

	@Modifying
	@Transactional
	@Query("DELETE FROM EmailToken e WHERE e.email = :email")
	void deleteByEmail(@Param("email") String email);

}
