package com.eventhub.registration.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eventhub.event.entity.Event;
import com.eventhub.registration.enums.RegistrationStatus;
import com.eventhub.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","event_id"}))
@Getter
@Setter
@RequiredArgsConstructor
public class EventRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private Event event;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RegistrationStatus status;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime registeredAt;
	
}
