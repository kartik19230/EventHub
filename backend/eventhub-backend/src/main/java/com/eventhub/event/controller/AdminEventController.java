package com.eventhub.event.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.event.dto.response.EventResponse;
import com.eventhub.event.dto.response.EventSummaryResponse;
import com.eventhub.event.dto.response.PendingEventResponse;
import com.eventhub.event.service.AdminEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/events")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminEventController {
	
	private final AdminEventService adminEventService;

	@GetMapping("/pending")
	public ResponseEntity<Page<EventSummaryResponse>> reviewPendingEvent(
					@RequestParam(required =  false,defaultValue = "1")Integer pageNumber){
		
		Page<EventSummaryResponse> response = adminEventService.getPendingEvents(pageNumber);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}/monitor")
	public ResponseEntity<EventResponse> monitorPendingEvent(@PathVariable Integer id){
		
		EventResponse response = adminEventService.getSubmittedEvent(id);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/approve")
	public ResponseEntity<PendingEventResponse> approve(@PathVariable Integer id){
		
		PendingEventResponse response = adminEventService.approveEvent(id);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/reject")
	public ResponseEntity<PendingEventResponse> reject(@PathVariable Integer id,@RequestParam String message){
		
		PendingEventResponse response = adminEventService.rejectEvent(id,message);
		return ResponseEntity.ok(response);
	}
}
