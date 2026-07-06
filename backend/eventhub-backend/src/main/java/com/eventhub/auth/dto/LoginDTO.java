package com.eventhub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginDTO(
		
		@NotBlank(message = "Email is required")
		@Email(message = "Invalid email format")
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
