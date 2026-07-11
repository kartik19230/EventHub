package com.eventhub.event.service;

import org.springframework.data.domain.Page;

import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.enums.EventCategory;

public interface PublicEventService {
	
	Page<EventSummaryResponse> viewEventWithPagination(int pageNumber);
	
	EventResponse viewEventDetails(int eventId);
	
	Page<EventSummaryResponse> searchEvent(int pageNumber,String title);
	
	Page<EventSummaryResponse> filterEvent(int pageNumber,EventCategory filter);
	
	Page<EventSummaryResponse> sortedEvent(int pageNumber,String direction,String sortField);
}
