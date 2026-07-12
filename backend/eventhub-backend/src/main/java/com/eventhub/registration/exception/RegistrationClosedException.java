package com.eventhub.registration.exception;

public class RegistrationClosedException extends RuntimeException{

	public RegistrationClosedException(String message) {
		super(message);
	}
}
