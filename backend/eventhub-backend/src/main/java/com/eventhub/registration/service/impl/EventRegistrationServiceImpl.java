package com.eventhub.registration.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.common.exception.ResourceNotFoundException;
import com.eventhub.common.security.CurrentUserService;
import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.repository.EventRepository;
import com.eventhub.registration.dto.EventRegistrationResponse;
import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.registration.enums.RegistrationStatus;
import com.eventhub.registration.exception.DuplicateRegistrationException;
import com.eventhub.registration.exception.EventCapacityExceedException;
import com.eventhub.registration.exception.OrganizerCannotRegisterOwnEventException;
import com.eventhub.registration.exception.RegistrationClosedException;
import com.eventhub.registration.exception.RegistrationNotOpenException;
import com.eventhub.registration.mapper.EventRegistrationMapper;
import com.eventhub.registration.repository.EventRegistrationRepository;
import com.eventhub.registration.service.EventRegistrationService;
import com.eventhub.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventRegistrationServiceImpl implements EventRegistrationService {

	private final CurrentUserService currentUserService;
	private final EventRegistrationRepository eventRegistrationRepository;
	private final EventRepository eventRepository;
	private final EventRegistrationMapper eventRegistrationMapper;

	@Override
	public EventRegistrationResponse register(Integer id) {

		User user = currentUserService.getCurrentuser();

		Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));

		if (List.of(EventStatus.DRAFT, EventStatus.PENDING_APPROVAL, EventStatus.PUBLISHED)
				.contains(event.getStatus())) {
			throw new RegistrationNotOpenException("Registration is not open.");
		} else if (List.of(EventStatus.REGISTRATION_CLOSED,EventStatus.ONGOING,EventStatus.COMPLETED).contains(event.getStatus())) {
			throw new RegistrationClosedException("Registration has closed.");
		} else if (event.getStatus() != EventStatus.REGISTRATION_OPEN) {
			throw new RegistrationNotOpenException("Registration not available for this event");
		}
		
		if (event.getOrganizer().getId().equals(user.getId())) {
			throw new OrganizerCannotRegisterOwnEventException("Organizers cannot registered for their own event");
		}
		
		if (eventRegistrationRepository.existsByUserAndEvent(user, event)) {
			throw new DuplicateRegistrationException("You are already registered for this event.");
		}
		
		if (eventRegistrationRepository.countByEvent(event) >= event.getCapacity()) {
			throw new EventCapacityExceedException("Sorry, Registration has been declined. Event is already full.");
		}

	    EventRegistration eventRegistration = new EventRegistration();
	    eventRegistration.setStatus(RegistrationStatus.REGISTERED);
	    eventRegistration.setRegisteredAt(LocalDateTime.now());
	    eventRegistration.setUser(user);
	    eventRegistration.setEvent(event);
	    
	    EventRegistration registration = eventRegistrationRepository.save(eventRegistration);
	    
		return eventRegistrationMapper.registrationResponse(registration);
	}

}
