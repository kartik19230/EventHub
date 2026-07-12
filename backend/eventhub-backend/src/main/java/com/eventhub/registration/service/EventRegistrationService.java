package com.eventhub.registration.service;

import org.springframework.data.domain.Page;

import com.eventhub.registration.dto.CancelRegistrationResponse;
import com.eventhub.registration.dto.EventRegistrationResponse;

public interface EventRegistrationService {

	EventRegistrationResponse register(Integer id);
	
	Page<EventRegistrationResponse> myRegistration(Integer pageNumber);
	
	CancelRegistrationResponse cancelRegistration(Integer id);
}
