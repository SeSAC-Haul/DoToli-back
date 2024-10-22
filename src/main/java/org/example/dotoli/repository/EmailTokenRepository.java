package org.example.dotoli.repository;

import java.util.Optional;

import org.example.dotoli.domain.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

	Optional<Object> findByEmail(String email);

	Optional<Object> findByToken(String token);

}
