package com.eventhub.registration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.event.entity.Event;
import com.eventhub.user.entity.User;
import java.util.Optional;
import com.eventhub.registration.enums.RegistrationStatus;


public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer> {

	boolean existsByUserAndEventAndStatus(User user, Event event,RegistrationStatus status);
	
	long countByEventAndStatus(Event event, RegistrationStatus status);
	
	Page<EventRegistration> findByUser(User user,Pageable pageable);
	
	Optional<EventRegistration> findByUser(User user);
	
	Optional<EventRegistration> findByUserAndEventAndStatus(User user, Event event, RegistrationStatus status);
	
	Page<EventRegistration> findByEvent(Event event, Pageable pageable);
}
