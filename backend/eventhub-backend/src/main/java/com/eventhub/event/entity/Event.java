package com.eventhub.event.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.enums.EventVisibility;
import com.eventhub.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String title;
	
	private String description;

	@Column(nullable = false)
	private String venue;

	@Column(nullable = false)
	private Integer capacity;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private LocalDateTime startDateTime;

	@Column(nullable = false)
	private LocalDateTime endDateTime;

	@Column(nullable = false)
	private LocalDateTime registrationOpenAt;

	@Column(nullable = false)
	private LocalDateTime registrationCloseAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EventCategory category;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EventStatus status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EventVisibility visibility;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private User organizer;
	
	private String bannerImageUrl;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
}
