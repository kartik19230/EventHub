package com.eventhub.registration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.event.entity.Event;
import com.eventhub.user.entity.User;


public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {

	boolean existsByUserAndEvent(User user, Event event);
	
	long countByEvent(Event event);
	
	Page<EventRegistration> findByUser(User user,Pageable pageable);
}
