package com.eventhub.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.auth.dto.AuthResponseDTO;
import com.eventhub.auth.dto.AuthenticationResponse;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.dto.ResendVerificationDTO;
import com.eventhub.auth.dto.UserProfileResponse;
import com.eventhub.auth.service.AuthService;
import com.eventhub.user.entity.User;

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
	public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {

		AuthenticationResponse response = authService.loginUser(request, dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/verify")
	public ResponseEntity<AuthResponseDTO> verify(@RequestParam String token) {

		AuthResponseDTO response = authService.verifyUser(token);

		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/resend-verification")
	public ResponseEntity<AuthResponseDTO> resendVerificationToken(@RequestBody ResendVerificationDTO dto){
		
		System.out.println("API triggered");
		AuthResponseDTO response = authService.resendVerificationToken(dto);
		
		return ResponseEntity.ok(response);
	}

	@GetMapping("/me")
	public UserProfileResponse profile(@AuthenticationPrincipal User user) {

		return new UserProfileResponse(user.getId(),user.getEmail(),user.getRole());
	}

}
