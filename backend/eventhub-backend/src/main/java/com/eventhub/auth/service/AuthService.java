package com.eventhub.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventhub.auth.dto.AuthResponeDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.user.entity.User;
import com.eventhub.user.enums.Role;
import com.eventhub.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository repo;
	
	private final PasswordEncoder encoder;
	
	public AuthResponeDTO findUserByEmail(RegisterDTO dto) {
		
		if (repo.existsByEmail(dto.email())) {
			return new AuthResponeDTO("User already exists");
		}
		
		User user = User.builder()
						.username(dto.username())
						.email(dto.email())
						.password(encoder.encode(dto.email()))
						.role(Role.valueOf(dto.role().toUpperCase()))
						.isEmailVerified(false)
						.build();
		
		return new AuthResponeDTO("User saved");
	}
}
