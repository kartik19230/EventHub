package com.eventhub.event.service;

import org.springframework.data.domain.Page;

import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.dto.response.PendingEventResponse;

public interface AdminEventService {

	Page<EventSummaryResponse> getPendingEvents(int pageNumber);
	
	EventResponse getSubmittedEvent(Integer id);
	
	PendingEventResponse approveEvent(Integer id);
	
	PendingEventResponse rejectEvent(Integer id,String message);
}
