package com.eventhub.auth.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventhub.auth.dto.AuthResponseDTO;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.entity.VerificationToken;
import com.eventhub.auth.event.UserRegistrationEvent;
import com.eventhub.auth.exception.InvalidVerificationTokenException;
import com.eventhub.auth.mapper.AuthMapper;
import com.eventhub.auth.security.SecurityContextService;
import com.eventhub.common.exception.UserAlreadyExistException;
import com.eventhub.user.entity.User;
import com.eventhub.user.enums.Role;
import com.eventhub.user.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEndcoder;
	private final AuthenticationManager authManager;
	private final VerificationTokenService verificationTokenService;
	private final ApplicationEventPublisher eventPublisher;
	
	private final SecurityContextService securityContextService;
	private final AuthMapper authMapper;
	
	@Transactional
	public AuthResponseDTO registerUser(RegisterDTO dto) throws MessagingException{
		
		if (userRepo.existsByEmail(dto.email())) {
			throw new UserAlreadyExistException("User already exist");
		}
		
		User user = authMapper.toUser(dto);
		user.setPassword(passwordEndcoder.encode(dto.password()));
		user.setRole(Role.ATTENDEE);
		user.setIsEmailVerified(false);
		
		User registeredUser = userRepo.save(user);
		
		VerificationToken token = verificationTokenService.createVerificationToken(user);
		
		eventPublisher.publishEvent(new UserRegistrationEvent(dto.email(),dto.username(),
				token.getToken()));
		
		return new AuthResponseDTO("User saved");
	}
	
	public AuthResponseDTO loginUser(HttpServletRequest request,LoginDTO dto) {
		
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
		
		securityContextService.establishAuthenticationSession(authentication, request);
		
		return new AuthResponseDTO("Successfully Logged In");
	}
	
	@Transactional
	public AuthResponseDTO verifyUser(String token){
		
		VerificationToken verToken = verificationTokenService.validateVerificationToken(token);
		
		User user = verToken.getUser();
		
		user.setIsEmailVerified(true);
		
		userRepo.save(user);
		
		verificationTokenService.deleteVerificationToken(user);
		
		return new AuthResponseDTO("Email Verified Successfully");
	}
}
