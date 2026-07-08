package com.eventhub.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.entity.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

	Event toEntity(CreateEventRequest request);
	
	@Mapping(target = "organizerName",source = "organizer.name")
	EventResponse toResponse(Event event);
	
	EventSummaryResponse toSummaryResponse(Event event);
}
