package com.eventhub.registration.dto;

import java.time.LocalDateTime;

import com.eventhub.registration.enums.RegistrationStatus;

import lombok.Builder;

@Builder
public record CancelRegistrationResponse(
		
		Integer id,
		String title,
		String username,
		RegistrationStatus status,
		LocalDateTime cancelAt
		) {}
