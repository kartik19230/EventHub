package com.eventhub.registration.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.registration.dto.CancelRegistrationResponse;
import com.eventhub.registration.dto.EventRegistrationResponse;
import com.eventhub.registration.service.EventRegistrationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/registrations")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ATTENDEE','ORGANIZER')")
public class EventRegistrationController {

	private final EventRegistrationService eventRegistrationService;
	
	@PostMapping("/events/{id}")
	public ResponseEntity<EventRegistrationResponse> register(@PathVariable Integer id) {

		EventRegistrationResponse response = eventRegistrationService.register(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}

	@GetMapping
	public ResponseEntity<Page<EventRegistrationResponse>> myRegistration(
			@RequestParam(required = false, defaultValue = "1") Integer pageNumber) {

		Page<EventRegistrationResponse> response = eventRegistrationService.myRegistration(pageNumber);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/cancel/{id}")
	public ResponseEntity<CancelRegistrationResponse> cancelRegistration(@RequestParam Integer id){
		
		CancelRegistrationResponse response = eventRegistrationService.cancelRegistration(id);
		return ResponseEntity.ok(response);
	}
	
}
