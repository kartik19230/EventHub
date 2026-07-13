package com.eventhub.event.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.auth.dto.MessageResponse;
import com.eventhub.event.dto.request.CreateEventRequest;
import com.eventhub.event.dto.request.UpdateEventRequest;
import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.dto.response.RegistrationResponse;
import com.eventhub.event.service.OrganizerEventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/organizer/events")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZER')")
public class OrganizerEventController {

	private final OrganizerEventService organizerEventService;
	
	@PostMapping
	public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
		
		EventResponse response = organizerEventService.createEvent(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@GetMapping
	public ResponseEntity<Page<EventSummaryResponse>> getMyEvents(
			@RequestParam(required = false,defaultValue = "1") int pageNumber) {
		
		Page<EventSummaryResponse> response = organizerEventService.getMyEvents(pageNumber);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EventResponse> getEventDetails(@PathVariable int id){
		
		EventResponse response = organizerEventService.getEventDetails(id);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EventResponse> updateDraftEvent(
			@Valid @RequestBody UpdateEventRequest request,@PathVariable Integer id){
		
		EventResponse response = organizerEventService.updateEvent(id, request);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteDraftEvent(@PathVariable Integer id){
		
		organizerEventService.deleteEvent(id);
		return ResponseEntity.ok(new MessageResponse("Event Deleted"));
	}
	
	@PatchMapping("/{id}/submit-for-review")
	public ResponseEntity<MessageResponse> submitDraftEvent(@PathVariable Integer id){
		
		String status = organizerEventService.submitForApproval(id);
		return ResponseEntity.ok(new MessageResponse("Event submitted for publishing, current status : " + status));
	}
	
	@GetMapping("/{id}/registrations")
	public ResponseEntity<Page<RegistrationResponse>> 
					getRegistrations(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
							@PathVariable Integer id){
		
		Page<RegistrationResponse> response = organizerEventService.getEventRegistrations(pageNumber, id);
		return ResponseEntity.ok(response);
	}
}
