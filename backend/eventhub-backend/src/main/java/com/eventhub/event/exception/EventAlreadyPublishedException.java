package com.eventhub.event.exception;

public class EventAlreadyPublishedException extends RuntimeException{

	public EventAlreadyPublishedException(String message) {
		super(message);
	}
}
