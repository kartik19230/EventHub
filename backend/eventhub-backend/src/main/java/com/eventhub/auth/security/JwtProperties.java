package com.eventhub.auth.security;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "jwt")
@Validated
public record JwtProperties(
		
		@NotBlank
        String secret,
        
        @NotNull
        Duration expiration,
        
        @NotBlank
        String issuer
) {
}
