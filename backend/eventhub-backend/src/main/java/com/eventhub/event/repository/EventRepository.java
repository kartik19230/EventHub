package com.eventhub.event.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.enums.EventVisibility;


public interface EventRepository extends JpaRepository<Event, Integer> {
	
	Page<Event> findByStatusAndVisibility(Pageable pageable,EventStatus status,EventVisibility visibility);
	
	Optional<Event> findByIdAndStatusAndVisibility(Integer id, EventStatus status,EventVisibility visibility);
	
	Page<Event> findByTitleContainingAllIgnoringCaseAndStatusAndVisibility(String title,Pageable pageable,EventStatus status,EventVisibility visibility);
	
	Page<Event> findByCategoryAndStatusAndVisibility(EventCategory category,Pageable pageable,EventStatus status,EventVisibility visibility);
}
