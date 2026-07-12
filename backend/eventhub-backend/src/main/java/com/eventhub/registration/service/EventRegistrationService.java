package com.eventhub.registration.service;

import com.eventhub.registration.dto.EventRegistrationResponse;

public interface EventRegistrationService {

	EventRegistrationResponse register(Integer id);
	
}
