package com.eventhub.event.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eventhub.common.exception.ResourceNotFoundException;
import com.eventhub.common.security.CurrentUserService;
import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.request.UpdateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.exception.CannotDeleteNonDraftEventException;
import com.eventhub.event.exception.CannotModifyNonDraftEventException;
import com.eventhub.event.exception.EventAccessDeniedException;
import com.eventhub.event.exception.EventAlreadyPublishedException;
import com.eventhub.event.exception.InvalidEventScheduleException;
import com.eventhub.event.exception.InvalidRegistrationWindowException;
import com.eventhub.event.mapper.EventMapper;
import com.eventhub.event.repository.EventRepository;
import com.eventhub.event.service.OrganizerEventService;
import com.eventhub.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizerEventServiceImpl implements OrganizerEventService {

	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	private final CurrentUserService currentUserService;

	private static final int PAGE_SIZE = 4;

	@Override
	public EventResponse createEvent(CreateEventRequest request) {

		validateEventSchedule(request.registrationOpenAt(),request.registrationCloseAt()
				,request.startDateTime(),request.endDateTime());

		User organizer = currentUserService.getCurrentuser();

		Event event = eventMapper.toEntity(request);

		event.setStatus(EventStatus.DRAFT);
		event.setOrganizer(organizer);

		Event savedEvent = eventRepository.save(event);

		return eventMapper.toResponse(savedEvent);
	}

	@Override
	public Page<EventSummaryResponse> getMyEvents(int pageNumber) {

		User user = currentUserService.getCurrentuser();

		Page<Event> myEvents = eventRepository.findByOrganizer(user, pageable(pageNumber));

		return myEvents.map(eventMapper::toSummaryResponse);
	}

	@Override
	public EventResponse getEventDetails(Integer id) {

		Event event = getOwnedEvent(id);

		return eventMapper.toResponse(event);
	}

	@Override
	public EventResponse updateEvent(Integer id, UpdateEventRequest request) {

		validateEventSchedule(request.registrationOpenAt(),request.registrationCloseAt()
				,request.startDateTime(),request.endDateTime());

		Event event = getOwnedEvent(id);

		if (event.getStatus() != EventStatus.DRAFT) {
			throw new CannotModifyNonDraftEventException(event.getStatus().name());
		}

		eventMapper.toUpdateEvent(request, event);

		Event updatedEvent = eventRepository.save(event);

		return eventMapper.toResponse(updatedEvent);
	}

	@Override
	public void deleteEvent(Integer id) {

		Event event = getOwnedEvent(id);
		
		if (event.getStatus() != EventStatus.DRAFT) {
			throw new CannotDeleteNonDraftEventException("Event already published");
		}

		eventRepository.delete(event);
	}

	@Override
	public String submitForApproval(Integer id) {

		Event event = getOwnedEvent(id);
		
		if (event.getStatus() == EventStatus.PUBLISHED) {
			throw new EventAlreadyPublishedException("Event already published");
		}else if (event.getStatus() != EventStatus.DRAFT) {
			throw new CannotModifyNonDraftEventException(event.getStatus().name());
		}

		event.setStatus(EventStatus.PENDING_APPROVAL);
		
		return event.getStatus().name();
	}

	private void validateEventSchedule(LocalDateTime regOpen, LocalDateTime regClose,LocalDateTime startDateTime,LocalDateTime endDateTime) {

		if (!regClose.isAfter(regOpen)) {
			throw new InvalidRegistrationWindowException("Regitration is closing before even it starts");
		}

		if (!endDateTime.isAfter(startDateTime)) {
			throw new InvalidEventScheduleException("Event end date and time must be after event starts");
		}

		if (regClose.isAfter(startDateTime)) {
			throw new InvalidRegistrationWindowException("Regitration must close before the event starts");
		}

	}

	private Event getOwnedEvent(Integer id) {
		
		User currentUser = currentUserService.getCurrentuser();
		
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found Exception"));
		
		if (!event.getOrganizer().getId().equals(currentUser.getId())) {
			throw new EventAccessDeniedException("Unauthorized Access");
		}
		
		return event;
			
	}

	private Pageable pageable(int pageNumber) {

		return PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("startDateTime").ascending());
	}

}
