package com.eventhub.event.exception;

public class CannotModifyNonDraftEventException extends RuntimeException{

	public CannotModifyNonDraftEventException(String currentStatus) {
		super(currentStatus);
	}
}
