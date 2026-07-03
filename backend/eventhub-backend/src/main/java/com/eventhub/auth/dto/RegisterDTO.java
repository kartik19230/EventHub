package com.eventhub.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
public record RegisterDTO(
		
		@NotBlank(message = "Name is required")
		@Size(min = 3, max = 50, message = "Username must be in between 3 to 50 characters")
		String username,
		
		@NotBlank(message = "Email is Required")
		@Email(message = "Invalid Email format")
		String email,
		
		@NotBlank(message = "Password is required")
		@Size(min = 6,max = 30, message = "Password must be between 6 to 30 characters")
		String password
		) {}
