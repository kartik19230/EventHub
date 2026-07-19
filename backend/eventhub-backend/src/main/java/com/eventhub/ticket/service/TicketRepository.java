package com.eventhub.ticket.service;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.ticket.entity.Ticket;
import java.util.List;
import java.util.Optional;

import com.eventhub.user.entity.User;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

	@EntityGraph(attributePaths = {"registration", "registration.event"})
	List<Ticket> findByRegistration_UserOrderByCreatedAtDesc(User registration_User);
	
	Optional<Ticket> findByTicketNumber(String ticketNumber);
}
