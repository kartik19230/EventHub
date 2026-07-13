package com.eventhub.event.dto.response;

import java.time.LocalDateTime;

import com.eventhub.registration.enums.RegistrationStatus;

public record RegistrationResponse(
		
		Integer registrationId,
		String attendeeName,
		String attendeeEmail,
		RegistrationStatus status,
		LocalDateTime registeredAt) {

}
