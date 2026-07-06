package com.eventhub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
		@Schema(
			    description = "Username",
			    example = "Kartik Shinde"
			)
		String username,
		
		@NotBlank(message = "Email is Required")
		@Email(message = "Invalid Email format")
		@Schema(
			    description = "User email address",
			    example = "kartik@example.com"
			)
		String email,
		
		@NotBlank(message = "Password is required")
		@Size(min = 6,max = 30, message = "Password must be between 6 to 30 characters")
		@Schema(
			    description = "User password",
			    example = "Password@123"
			)
		String password
		) {}
