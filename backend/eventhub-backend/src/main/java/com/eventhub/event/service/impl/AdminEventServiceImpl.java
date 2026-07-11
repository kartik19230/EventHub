package com.eventhub.event.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eventhub.common.exception.ResourceNotFoundException;
import com.eventhub.common.security.CurrentUserService;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.dto.response.PendingEventResponse;
import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.enums.EventVisibility;
import com.eventhub.event.exception.EventNotPendingForApprovalException;
import com.eventhub.event.mapper.EventMapper;
import com.eventhub.event.repository.EventRepository;
import com.eventhub.event.service.AdminEventService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEventServiceImpl implements AdminEventService {

	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	private final CurrentUserService currentUserService;

	private static final int PAGE_SIZE = 4;

	@Override
	public Page<EventSummaryResponse> getPendingEvents(int pageNumber) {

		Page<Event> events = eventRepository.findByStatusAndVisibility(pageable(pageNumber),
				EventStatus.PENDING_APPROVAL, EventVisibility.PUBLIC);

		return events.map(eventMapper::toSummaryResponse);
	}

	@Override
	public EventResponse getSubmittedEvent(Integer id) {

		Event event = getPendingEvent(id, "monitored");
			
		return eventMapper.toResponse(event);
	}

	@Override
	public PendingEventResponse approveEvent(Integer id) {

		Event event = getPendingEvent(id, "approve");

		event.setStatus(EventStatus.PUBLISHED);

		PendingEventResponse response = new PendingEventResponse(event.getStatus(), LocalDateTime.now(),
				currentUserService.getCurrentuser().getName(),"Successfully Published");

		return response;
	}

	@Override
	public PendingEventResponse rejectEvent(Integer id,String message) {

		Event event = getPendingEvent(id, "reject");

		event.setStatus(EventStatus.REJECTED);

		PendingEventResponse response = new PendingEventResponse(event.getStatus(), LocalDateTime.now(),
				currentUserService.getCurrentuser().getName(),message);

		return response;
	}
	
	private Event getPendingEvent(Integer id,String status) {
		
		Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));

		if (event.getStatus() != EventStatus.PENDING_APPROVAL) {
			throw new EventNotPendingForApprovalException("Only Pending Event can be " + status);
		}
		
		return event;
	}

	private Pageable pageable(int pageNumber) {

		return PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("startDateTime").ascending());
	}

}
