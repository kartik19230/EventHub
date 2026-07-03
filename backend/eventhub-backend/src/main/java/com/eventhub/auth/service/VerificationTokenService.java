package com.eventhub.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eventhub.auth.entity.VerificationToken;
import com.eventhub.auth.exception.InvalidVerificationTokenException;
import com.eventhub.auth.exception.VerificationTokenExpiredException;
import com.eventhub.auth.repository.VerificationTokenRepository;
import com.eventhub.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

	private final VerificationTokenRepository verificationTokenRepo;

	@Transactional
	public VerificationToken createVerificationToken(User user) {

		String token = UUID.randomUUID().toString();
		LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

		return verificationTokenRepo.findByUser(user).map(existingToken -> {
			existingToken.setToken(token);
			existingToken.setExpiryDate(expiry);
			return verificationTokenRepo.save(existingToken);
		}).orElseGet(() -> {
			VerificationToken verificationToken = new VerificationToken(token, user, expiry);
			return verificationTokenRepo.save(verificationToken);
		});
	}

	public void deleteVerificationToken(User user) {

		verificationTokenRepo.deleteByUser(user);
	}

	public VerificationToken getVerificationToken(String token) {

		return verificationTokenRepo.findByToken(token)
				.orElseThrow(() -> new InvalidVerificationTokenException("Invalid Verification Token"));
	}

	public VerificationToken validateVerificationToken(String token) {

		VerificationToken verificationToken = verificationTokenRepo.findByToken(token)
				.orElseThrow(() -> new InvalidVerificationTokenException("Invalid VerificationToken"));

		if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new VerificationTokenExpiredException("Token Expired");
		}

		return verificationToken;
	}
}
