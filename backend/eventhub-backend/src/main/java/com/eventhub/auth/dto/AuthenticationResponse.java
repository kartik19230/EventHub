package com.eventhub.auth.dto;

public record AuthenticationResponse(
		String accesToken,
		String tokenType
		) {}
