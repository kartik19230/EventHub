package com.eventhub.registration.exception;

public class EventCapacityExceedException extends RuntimeException{

	public EventCapacityExceedException(String message) {
		super(message);
	}
}
