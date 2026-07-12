package com.eventhub.event.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventCategory;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.enums.EventVisibility;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import com.eventhub.user.entity.User;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	Page<Event> findByOrganizer(User organizer,Pageable pageable);
	
	Page<Event> findByStatusAndVisibility(Pageable pageable,EventStatus status,EventVisibility visibility);
	
	Page<Event> findByStatusInAndVisibility(Collection<EventStatus> statuses,EventVisibility visibility,Pageable pageable);
	
	Optional<Event> findByIdAndStatusAndVisibility(Integer id, EventStatus status,EventVisibility visibility);
	
	Optional<Event> findByIdAndStatusInAndVisibility(Integer id, Collection<EventStatus> statuses,EventVisibility visibility);
	
	Page<Event> findByTitleContainingAllIgnoringCaseAndStatusAndVisibility(String title,Pageable pageable,EventStatus status,EventVisibility visibility);
	
	Page<Event> findByCategoryAndStatusAndVisibility(EventCategory category,Pageable pageable,EventStatus status,EventVisibility visibility);
	
	Page<Event> findByCategoryAndStatusInAndVisibility(EventCategory category,Pageable pageable,Collection<EventStatus> statuses,EventVisibility visibility);

	List<Event> findByStatusAndRegistrationOpenAtLessThanEqual(EventStatus status,LocalDateTime time);
	
	List<Event> findByStatusAndRegistrationCloseAtLessThanEqual(EventStatus status,LocalDateTime time);
	
	List<Event> findByStatusAndStartDateTimeLessThanEqual(EventStatus status,LocalDateTime time);
	
	List<Event> findByStatusAndEndDateTimeLessThanEqual(EventStatus status, LocalDateTime time);
}
