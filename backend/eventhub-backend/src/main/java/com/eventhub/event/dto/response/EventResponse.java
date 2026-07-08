package com.eventhub.event.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.enums.EventVisibility;

public record EventResponse(
		
		Integer id,
		
		String title,
		
		String description,
		
		EventCategory category,
		
		String venue,
		
		LocalDateTime startDateTime,
		
		LocalDateTime endDateTime,
		
		LocalDateTime registrationOpenAt,
		
		LocalDateTime registrationCloseAt,
		
		Integer capacity,
		
		BigDecimal price,
		
		EventVisibility visibility,
		
		EventStatus status,
		
		String bannerImageUrl,
		
		String organizerName,
		
		LocalDateTime createdAt,
		
		LocalDateTime updatedAt
		
		) {

}
