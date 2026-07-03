package com.eventhub.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.auth.entity.VerificationToken;
import java.util.Optional;
import com.eventhub.user.entity.User;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);
	
	Optional<VerificationToken> findByUser(User user);
	
	boolean existsByUser(User user);
	
	void deleteByUser(User user);
}
