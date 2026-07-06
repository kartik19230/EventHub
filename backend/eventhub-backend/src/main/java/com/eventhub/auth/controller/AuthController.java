package com.eventhub.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.auth.dto.MessageResponse;
import com.eventhub.auth.dto.AuthenticationResponse;
import com.eventhub.auth.dto.LoginDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.dto.ResendVerificationDTO;
import com.eventhub.auth.dto.UserProfileResponse;
import com.eventhub.auth.service.AuthService;
import com.eventhub.user.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Tag(
		name = "Authentication",
		description = "API's for registering, authentication, email verification and profile management"
		)
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	@SecurityRequirements
	@Operation(
			summary = "Registers a new user",
			description = "Creates a new verification account and sends an email verification link"
			)
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Registration Successful"),
		@ApiResponse(responseCode = "409", description = "Email already exist"),
		@ApiResponse(responseCode = "400", description = "validation failed"),
		@ApiResponse(responseCode = "500", description = "Failed to send verification email or Internal server error")
	})
	public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterDTO dto) throws MessagingException {

		MessageResponse response = authService.registerUser(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	@SecurityRequirements
	@Operation(
			summary = "Authenticating a user",
			description = "Authenticates the user and returns JWT access token")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Authentication Successful"),
		@ApiResponse(responseCode = "401", description = "Invalid Credentials"),
		@ApiResponse(responseCode = "403", description = "Account not verified")
	})
	public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {

		AuthenticationResponse response = authService.loginUser(request, dto);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/verify")
	@SecurityRequirements
	@Operation(
			summary = "Verifies the registered user email",
			description ="Activates the user account using verification token")
	public ResponseEntity<MessageResponse> verify(@RequestParam String token) {

		MessageResponse response = authService.verifyUser(token);

		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/resend-verification")
	@SecurityRequirements
	@Operation(
			summary = "Resend-verification token for email",
			description = "Resends the verification token through the email for verification")
	public ResponseEntity<MessageResponse> resendVerificationToken(@RequestBody ResendVerificationDTO dto){
		
		System.out.println("API triggered");
		MessageResponse response = authService.resendVerificationToken(dto);
		
		return ResponseEntity.ok(response);
	}

	@GetMapping("/me")
	@Operation(
			summary = "Get authenticated user",
			description = "Returns information about currently the authenticated user")
	public ResponseEntity<UserProfileResponse> profile(@AuthenticationPrincipal User user) {

		return ResponseEntity.ok(new UserProfileResponse(user.getId(),user.getEmail(),user.getRole()));
	}
	

}
