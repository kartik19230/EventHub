package com.eventhub.auth.event;


public record UserRegistrationEvent(
		String email,
		String username,
		String verificationToken
		) {}

