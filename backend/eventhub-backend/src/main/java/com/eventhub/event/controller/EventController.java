package com.eventhub.event.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventService;
	
	@PostMapping("/event")
	@PreAuthorize("hasRole('ORGANIZER')")
	public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
		
		EventResponse response = eventService.createEvent(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	
}
