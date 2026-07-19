package com.eventhub.ticket.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.ticket.enums.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false,unique = true)
	private String ticketNumber;
	
	@OneToOne
	@JoinColumn(nullable = true,unique = true)
	private EventRegistration registration;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
}
