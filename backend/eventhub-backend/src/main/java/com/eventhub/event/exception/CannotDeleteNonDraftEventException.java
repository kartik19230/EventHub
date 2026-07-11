package com.eventhub.event.exception;

public class CannotDeleteNonDraftEventException extends RuntimeException{

	public CannotDeleteNonDraftEventException(String currentStatus) {
		super(currentStatus);
	}
}
