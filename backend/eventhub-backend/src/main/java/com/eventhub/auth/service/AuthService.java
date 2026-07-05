package com.eventhub.auth.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventhub.auth.dto.MessageResponse;
import com.eventhub.auth.dto.AuthenticationResponse;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.dto.ResendVerificationDTO;
import com.eventhub.auth.entity.VerificationToken;
import com.eventhub.auth.event.UserRegistrationEvent;
import com.eventhub.auth.exception.UserAlreadyVerifiedException;
import com.eventhub.auth.mapper.AuthMapper;
import com.eventhub.auth.security.JwtService;
import com.eventhub.common.exception.UserAlreadyExistException;
import com.eventhub.common.exception.UserNotFoundException;
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
	private final JwtService jwtService;
	
	private final AuthMapper authMapper;
	
	@Transactional
	public MessageResponse registerUser(RegisterDTO dto) throws MessagingException{
		
		if (userRepo.existsByEmail(dto.email())) {
			throw new UserAlreadyExistException("User already exist");
		}
		
		User user = authMapper.toUser(dto);
		user.setPassword(passwordEndcoder.encode(dto.password()));
		user.setRole(Role.ATTENDEE);
		user.setIsEmailVerified(false);
		
		User registeredUser = userRepo.save(user);
		
		VerificationToken token = verificationTokenService.createVerificationToken(user);
		
		eventPublisher.publishEvent(new UserRegistrationEvent(registeredUser.getEmail(),dto.username(),
				token.getToken()));
		
		return new MessageResponse("User saved");
	}
	
	public AuthenticationResponse loginUser(HttpServletRequest request,LoginDTO dto) {
		
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
		
	    User user = (User)authentication.getPrincipal();
	    
	    String token = jwtService.generateToken(user);
		
		return new AuthenticationResponse(token, "Bearer ");
	}
	
	@Transactional
	public MessageResponse verifyUser(String token){
		
		VerificationToken verToken = verificationTokenService.validateVerificationToken(token);
		
		User user = verToken.getUser();
		
		user.setIsEmailVerified(true);
		
		userRepo.save(user);
		
		verificationTokenService.deleteVerificationToken(user);
		
		return new MessageResponse("Email Verified Successfully");
	}

	@Transactional
	public MessageResponse resendVerificationToken(ResendVerificationDTO dto) {
		 
		User user = userRepo.findByEmail(dto.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		if (user.getIsEmailVerified()) {
			throw new UserAlreadyVerifiedException("User Already Verified");
		}
		
		VerificationToken token = verificationTokenService.regenerateVerificationToken(user);
		
		eventPublisher.publishEvent(new UserRegistrationEvent(user.getEmail(), user.getUsername(), token.getToken()));
		
		return new MessageResponse("Token Resended Successfully");
	}
}
