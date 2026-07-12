package com.eventhub.registration.exception;

public class UserNotRegisteredException extends RuntimeException{

	public UserNotRegisteredException(String message) {
		super(message);
	}
}
