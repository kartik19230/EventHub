package com.eventhub.event.exception;

public class EventAccessDeniedException extends RuntimeException{

	public EventAccessDeniedException(String message) {
		super(message);
	}
}
