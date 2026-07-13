package com.eventhub.event.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.request.UpdateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.dto.response.RegistrationResponse;
import com.eventhub.event.entity.Event;
import com.eventhub.registration.entity.EventRegistration;

@Mapper(componentModel = "spring")
public interface EventMapper {

	Event toEntity(CreateEventRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void toUpdateEvent(UpdateEventRequest request,@MappingTarget Event event);
	
	@Mapping(target = "organizerName",source = "organizer.name")
	EventResponse toResponse(Event event);
	
	EventSummaryResponse toSummaryResponse(Event event);
	
	@Mapping(target = "registrationId", source = "id")
	@Mapping(target = "attendeeName", source = "user.name")
	@Mapping(target = "attendeeEmail", source = "user.email")
	RegistrationResponse getRegistrations(EventRegistration eventRegistration);
}
