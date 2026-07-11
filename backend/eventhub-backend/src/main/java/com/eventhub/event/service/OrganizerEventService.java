package com.eventhub.event.service;

import org.springframework.data.domain.Page;

import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.request.UpdateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;

public interface OrganizerEventService {

	EventResponse createEvent(CreateEventRequest request);
	
	Page<EventSummaryResponse> getMyEvents(int pageNumber);
	
	EventResponse getEventDetails(Integer id);
	
	EventResponse updateEvent(Integer id,UpdateEventRequest request);
	
	void deleteEvent(Integer id);
	
	String submitForApproval(Integer id);
}
