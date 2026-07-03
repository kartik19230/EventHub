package com.eventhub.auth.entity;

public class UserNotVerifiedException extends RuntimeException{

	public UserNotVerifiedException(String message) {
		super(message);
	}
}
