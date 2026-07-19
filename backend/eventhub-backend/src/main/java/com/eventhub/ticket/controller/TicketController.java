package com.eventhub.ticket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.ticket.dto.response.TicketResponse;
import com.eventhub.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ATTENDEE')")
public class TicketController {
	
	private final TicketService ticketService;
	
	@GetMapping("/myTickets")
	public ResponseEntity<List<TicketResponse>> getMyTickets(){
		
		return ResponseEntity.ok(ticketService.getTickets());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TicketResponse> getTicket(@PathVariable Long id){
		
		TicketResponse response = ticketService.getTicket(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/number/{ticketId}")
	public ResponseEntity<TicketResponse> getTicket(@PathVariable String ticketId){
		
		TicketResponse response = ticketService.getTicket(ticketId);
		return ResponseEntity.ok(response);
	}

}
