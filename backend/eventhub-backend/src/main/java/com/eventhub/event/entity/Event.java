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
	
	private String title;
	private String description;
	private String venue;
	
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private LocalDateTime registrationOpenAt;
	private LocalDateTime registrationCloseAt;
	
	private Integer capacity;
	private BigDecimal price;
	private String bannerImageUrl;
	
	@Enumerated(EnumType.STRING)
	private EventStatus status;
	
	@Enumerated(EnumType.STRING)
	private EventCategory category;
	
	@Enumerated(EnumType.STRING)
	private EventVisibility visibility;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User organizer; 
	
}
