package com.eventhub.event.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventVisibility;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record CreateEventRequest(
		
		@NotBlank
		String title,
		
		@NotBlank
		String description,
		
		@NotNull
		EventCategory category,
		
		@NotBlank
		String venue,
	
		@NotNull
		@Future
		LocalDateTime startDateTime,
		
		@NotNull
		@Future
		LocalDateTime endDateTime,
		
		@NotNull
		@Future
		LocalDateTime registrationOpenAt,
		
		@NotNull
		@Future
		LocalDateTime registrationCloseAt,
		
		@Positive
		Integer capacity,
		
		@PositiveOrZero
		@NotNull
		BigDecimal price,
		
		@NotNull
		EventVisibility visibility,
		
		String bannerImageUrl
		
		) {}
