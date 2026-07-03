package com.eventhub.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.auth.dto.AuthResponseDTO;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO dto) throws MessagingException {

		AuthResponseDTO response = authService.registerUser(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {

		AuthResponseDTO response = authService.loginUser(request, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/verify")
	public ResponseEntity<AuthResponseDTO> verify(@RequestParam String token) {

		AuthResponseDTO response = authService.verifyUser(token);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/me")
	public String profile(Authentication authentication) {

		if (authentication == null) {
			return "authentication is null";
		}
		return authentication.getName();
	}

}
