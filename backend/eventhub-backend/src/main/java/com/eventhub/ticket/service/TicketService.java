package com.eventhub.ticket.service;

import java.util.List;

import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.ticket.dto.response.TicketResponse;

public interface TicketService {
	
	void issueTicket(EventRegistration registration);
		
	List<TicketResponse> getTickets();
	
	TicketResponse getTicket(Long id);
	
	TicketResponse getTicket(String ticketId);
}
