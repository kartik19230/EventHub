package com.eventhub.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendVerificationDTO {

	@Email(message = "Invalid Email format")
	@NotBlank(message ="Email is required")
	@Schema(
		    description = "User email address",
		    example = "kartik@example.com"
		)
	private String email;
}
