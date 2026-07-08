package com.eventhub.event.service;

import org.springframework.stereotype.Service;

import com.eventhub.common.security.CurrentUserService;
import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.exception.InvalidEventScheduleException;
import com.eventhub.event.exception.InvalidRegistrationWindowException;
import com.eventhub.event.mapper.EventMapper;
import com.eventhub.event.repository.EventRepository;
import com.eventhub.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService{
	
	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	private final CurrentUserService currentUserService;
	
	@Override
	public EventResponse createEvent(CreateEventRequest request){

		validateEventSchedule(request);
		
		User organizer = currentUserService.getCurrentuser();
		
		Event event = eventMapper.toEntity(request);
		
		event.setStatus(EventStatus.DRAFT);
		event.setOrganizer(organizer);
		
		Event savedEvent = eventRepository.save(event);
		
		return eventMapper.toResponse(savedEvent);
	}
	
	private void validateEventSchedule(CreateEventRequest request) {
		
		if(!request.registrationCloseAt().isAfter(request.registrationOpenAt())) {
			throw new InvalidRegistrationWindowException("Regitration is closing before even it starts	");
		}
		
		if (!request.endDateTime().isAfter(request.startDateTime())) {
			throw new InvalidEventScheduleException("Event end date and time must be after event starts");
		}
		
		if (request.registrationCloseAt().isAfter(request.startDateTime())) {
			throw new InvalidRegistrationWindowException("Regitration must close before the event starts");
		}
	}

	
}
