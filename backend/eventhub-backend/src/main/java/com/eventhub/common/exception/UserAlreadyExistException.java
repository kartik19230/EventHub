package com.eventhub.common.exception;

public class UserAlreadyExistException extends RuntimeException{

	public UserAlreadyExistException(String message) {
		super(message);
	}
}
