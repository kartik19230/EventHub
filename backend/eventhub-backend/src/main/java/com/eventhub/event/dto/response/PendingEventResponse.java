package com.eventhub.event.dto.response;

import java.time.LocalDateTime;

import com.eventhub.event.enums.EventStatus;

public record PendingEventResponse(
		
		EventStatus status,
		LocalDateTime administeredAt,
		String administrator,
		String message
		
		) {}
