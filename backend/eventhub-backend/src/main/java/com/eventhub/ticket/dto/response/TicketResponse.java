package com.eventhub.ticket.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketResponse(
		
		String ticketNumber,
		String title,
		String venue,
		BigDecimal price,
		LocalDateTime startDateTime,
		LocalDateTime endDateTime,
		LocalDateTime createdAt
		
		) {}
