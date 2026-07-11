package com.eventhub.event.exception;

public class EventNotPendingForApprovalException extends RuntimeException {

	public EventNotPendingForApprovalException(String message) {
		super(message);
	}
}
