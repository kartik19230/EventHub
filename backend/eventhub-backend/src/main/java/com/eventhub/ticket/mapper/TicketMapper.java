package com.eventhub.ticket.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventhub.ticket.dto.response.TicketResponse;
import com.eventhub.ticket.entity.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {

	@Mapping(source = "registration.event.title", target = "title")
	@Mapping(source = "registration.event.venue", target = "venue")
	@Mapping(source = "registration.event.price", target = "price")
	@Mapping(source = "registration.event.startDateTime", target = "startDateTime")
	@Mapping(source = "registration.event.endDateTime", target = "endDateTime")
	TicketResponse ticketResponse(Ticket ticket);
	
	List<TicketResponse> listOfTicketResponse(List<Ticket> tickets);
	
}
