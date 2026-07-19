package com.eventhub.ticket.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eventhub.common.exception.ResourceNotFoundException;
import com.eventhub.common.security.CurrentUserService;
import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.ticket.dto.response.TicketResponse;
import com.eventhub.ticket.entity.Ticket;
import com.eventhub.ticket.enums.TicketStatus;
import com.eventhub.ticket.exception.TicketAccessDeniedException;
import com.eventhub.ticket.mapper.TicketMapper;
import com.eventhub.ticket.service.TicketRepository;
import com.eventhub.ticket.service.TicketService;
import com.eventhub.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{
	
	private final TicketRepository ticketRepository;
	private final CurrentUserService currentUserService;
	private final TicketMapper ticketMapper;
	
	@Override
	public void issueTicket(EventRegistration registration) {
		
		Ticket ticket = new Ticket();
		
		ticket.setRegistration(registration);
		ticket.setStatus(TicketStatus.ACTIVE);
		ticket.setTicketNumber("TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
		
		ticketRepository.save(ticket);
	}
	
	@Override
	public List<TicketResponse> getTickets() {

		User user = currentUserService.getCurrentuser();
		
		List<Ticket> allTickets = ticketRepository.findByRegistration_UserOrderByCreatedAtDesc(user);
		
		return ticketMapper.listOfTicketResponse(allTickets);
	}
	
	@Override
	public TicketResponse getTicket(Long id) {

		Ticket ticket = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
		
		User user = currentUserService.getCurrentuser();
		
			if (!ticket.getRegistration().getUser().getId().equals(user.getId())) {
				throw new TicketAccessDeniedException("Ticket access denied");
			}
		
		return ticketMapper.ticketResponse(ticket);
	}

	@Override
	public TicketResponse getTicket(String ticketId) {

		Ticket ticket = ticketRepository.findByTicketNumber(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
		
		User user = currentUserService.getCurrentuser();
		
			if (!ticket.getRegistration().getUser().getId().equals(user.getId())) {
				throw new TicketAccessDeniedException("Ticket access denied");
			}
		
		return ticketMapper.ticketResponse(ticket);
	}
}
