package com.eventhub.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendVerificationDTO {

	@Email(message = "Invalid Email format")
	@NotBlank(message ="Email is required")
	private String email;
}
