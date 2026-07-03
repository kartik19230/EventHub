package com.eventhub.auth.exception;

public class InvalidVerificationTokenException extends RuntimeException{

	public InvalidVerificationTokenException(String message) {
		super(message);
	}
}
