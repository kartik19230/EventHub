package com.eventhub.registration.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.registration.enums.RegistrationStatus;

public record EventRegistrationResponse(
		
		Integer id,
		String attendeeName,
		Integer eventId,
		String title,
		RegistrationStatus status,
		LocalDateTime registeredAt,
		BigDecimal price,
		LocalDateTime eventStartDateTime,
		LocalDateTime eventEndDateTime,
		String organizedBy		
		) {}
