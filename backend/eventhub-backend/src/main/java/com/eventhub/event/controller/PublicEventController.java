package com.eventhub.event.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.service.PublicEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public-event")
@RequiredArgsConstructor
public class PublicEventController {

	private final PublicEventService publicEventService;
	
	@GetMapping("/event")
	public ResponseEntity<Page<EventSummaryResponse>> getEvent(
			@RequestParam(required = false,defaultValue = "1") int pageNumber){
		
		Page<EventSummaryResponse> response = publicEventService.viewEventWithPagination(pageNumber);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/event/{id}")
	public ResponseEntity<EventResponse> getEventById(@PathVariable int id){
		
		EventResponse response = publicEventService.viewEventDetails(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<EventSummaryResponse>> searchEvent(
			@RequestParam(required = false,defaultValue = "1") int pageNumber,
			@RequestParam(required = false) String title){
		
		Page<EventSummaryResponse> response = publicEventService.searchEvent(pageNumber,title);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<Page<EventSummaryResponse>> filterEvent(
			@RequestParam(required = false,defaultValue = "1") int pageNumber,
			@RequestParam(required = false) EventCategory category){
		
		Page<EventSummaryResponse> response = 
				publicEventService.filterEvent(pageNumber, category);
		
		return ResponseEntity.ok(response);
	}
}
