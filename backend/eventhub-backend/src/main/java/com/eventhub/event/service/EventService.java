package com.eventhub.event.service;

import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.response.EventResponse;

public interface EventService {

	EventResponse createEvent(CreateEventRequest request);
}
