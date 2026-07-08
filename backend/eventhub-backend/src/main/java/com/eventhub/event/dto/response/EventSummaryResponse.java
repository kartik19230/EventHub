package com.eventhub.event.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventStatus;

public record EventSummaryResponse (
		
		Integer id,
		
		String title,
		
		EventCategory category,
		
		String venue,
		
		LocalDateTime startDateTime,
		
		BigDecimal price,
		
		String bannerImageUrl,
		
		EventStatus status
		){}
