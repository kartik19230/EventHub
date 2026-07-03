package com.eventhub.auth.exception;

public class VerificationTokenExpiredException extends RuntimeException{

	public VerificationTokenExpiredException(String message) {
		super(message);
	}
}
