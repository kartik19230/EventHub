package com.eventhub.auth.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.eventhub.auth.dto.AuthResponseDTO;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.common.exception.UserAlreadyExistException;
import com.eventhub.user.entity.User;
import com.eventhub.user.enums.Role;
import com.eventhub.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository repo;
	
	private final PasswordEncoder encoder;
	
	private final AuthenticationManager authManager;
	
	public AuthResponseDTO registerUser(RegisterDTO dto) {
		
		if (repo.existsByEmail(dto.email())) {
			throw new UserAlreadyExistException("User already exist");
		}
		
		User user = User.builder()
						.username(dto.username())
						.email(dto.email())
						.password(encoder.encode(dto.password()))
						.role(Role.valueOf(dto.role().toUpperCase()))
						.isEmailVerified(false)
						.build();
		
		repo.save(user);
		
		return new AuthResponseDTO("User saved");
	}
	
	public AuthResponseDTO loginUser(HttpServletRequest request,LoginDTO dto) {
		
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		
		SecurityContextHolder.setContext(context);
		
		HttpSession session = request.getSession(true);
		
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new AuthResponseDTO("Successfully Logged In");
	}
}
