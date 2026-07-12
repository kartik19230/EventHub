package com.eventhub.event.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eventhub.common.exception.ResourceNotFoundException;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.enums.EventVisibility;
import com.eventhub.event.mapper.EventMapper;
import com.eventhub.event.repository.EventRepository;
import com.eventhub.event.service.PublicEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {

	private final EventRepository eventRepository;
	private final EventMapper eventMapper;
	
	private static final int PAGE_SIZE = 4;
	
	private static List<EventStatus> visibleStatuses = List.of(
			EventStatus.PUBLISHED,
		    EventStatus.REGISTRATION_OPEN,
		    EventStatus.REGISTRATION_CLOSED,
		    EventStatus.ONGOING,
		    EventStatus.COMPLETED);
	
	@Override
	public Page<EventSummaryResponse> viewEventWithPagination(int pageNumber) {

		System.out.println("------------------------Run--------------------------------------");
		Pageable pageable = pageable(pageNumber);
		Page<Event> page = eventRepository.findByStatusInAndVisibility(new ArrayList<>(visibleStatuses),EventVisibility.PUBLIC,pageable);

		log.info("Statuses: {}",visibleStatuses);
		return page.map(eventMapper::toSummaryResponse);
	}

	@Override
	public EventResponse viewEventDetails(int eventId) {

		Event event = eventRepository.findByIdAndStatusInAndVisibility(eventId,visibleStatuses,EventVisibility.PUBLIC)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found"));

		return eventMapper.toResponse(event);
	}

	@Override
	public Page<EventSummaryResponse> searchEvent(int pageNumber, String title) {

		Pageable pageable = pageable(pageNumber);
		Page<Event> page;

		if (title == null) {
			page = eventRepository.findByStatusInAndVisibility(visibleStatuses,EventVisibility.PUBLIC,pageable);
		} else {
			page = eventRepository.findByTitleContainingAllIgnoringCaseAndStatusAndVisibility(title, pageable,
					EventStatus.PUBLISHED,EventVisibility.PUBLIC);
		}
		return page.map(event -> eventMapper.toSummaryResponse(event));
	}

	@Override
	public Page<EventSummaryResponse> filterEvent(int pageNumber, EventCategory category) {

		Pageable pageable = pageable(pageNumber);

		Page<Event> page;

		if (category == null) {

			page = eventRepository.findByStatusInAndVisibility(visibleStatuses,EventVisibility.PUBLIC,pageable);
		} else {

			page = eventRepository.findByCategoryAndStatusInAndVisibility(category, pageable, visibleStatuses,EventVisibility.PUBLIC);
		}

		return page.map(eventMapper::toSummaryResponse);
	}
	
	@Override
	public Page<EventSummaryResponse> sortedEvent(int pageNumber, String direction, String sortField) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Pageable pageable(int pageNumber) {
		
		return PageRequest.of(pageNumber - 1, PAGE_SIZE,Sort.by("startDateTime").ascending());
	}

	
}
