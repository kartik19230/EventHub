package com.eventhub.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventhub.registration.dto.EventRegistrationResponse;
import com.eventhub.registration.entity.EventRegistration;


@Mapper(componentModel = "spring")
public interface EventRegistrationMapper {

	@Mapping(target = "attendeeName", source = "user.name")
	@Mapping(target = "eventId", source = "event.id")
	@Mapping(target = "title", source = "event.title")
	@Mapping(target = "price", source = "event.price")
	@Mapping(target = "eventStartDateTime", source = "event.startDateTime")
	@Mapping(target = "eventEndDateTime", source = "event.endDateTime")
	@Mapping(target = "organizedBy", source = "event.organizer.name")
	EventRegistrationResponse registrationResponse(EventRegistration registration);
	
}

