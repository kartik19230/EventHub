package com.eventhub.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventhub.auth.dto.AuthResponseDTO;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.mapper.AuthMapper;
import com.eventhub.auth.security.SessionContextService;
import com.eventhub.common.exception.UserAlreadyExistException;
import com.eventhub.user.entity.User;
import com.eventhub.user.enums.Role;
import com.eventhub.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEndcoder;
	private final AuthenticationManager authManager;
	
	private final SessionContextService sessionContextService;
	private final AuthMapper authMapper;
	
	public AuthResponseDTO registerUser(RegisterDTO dto) {
		
		if (userRepo.existsByEmail(dto.email())) {
			throw new UserAlreadyExistException("User already exist");
		}
		
		User user = authMapper.toUser(dto);
		user.setPassword(passwordEndcoder.encode(dto.password()));
		user.setRole(Role.ATTENDEE);
		user.setIsEmailVerified(false);
		
		userRepo.save(user);
		
		return new AuthResponseDTO("User saved");
	}
	
	public AuthResponseDTO loginUser(HttpServletRequest request,LoginDTO dto) {
		
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
		
		sessionContextService.establishAuthenticationSession(authentication, request);
		
		return new AuthResponseDTO("Successfully Logged In");
	}
}
