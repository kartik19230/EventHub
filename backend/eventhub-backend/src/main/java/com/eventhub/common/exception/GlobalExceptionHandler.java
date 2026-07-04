package com.eventhub.common.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.print.DocFlavor.READER;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eventhub.auth.dto.AuthResponseDTO;
import com.eventhub.auth.exception.InvalidVerificationTokenException;
import com.eventhub.auth.exception.UserAlreadyVerifiedException;
import com.eventhub.auth.exception.UserNotVerifiedException;
import com.eventhub.auth.exception.VerificationTokenExpiredException;

import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handledMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handledUserAlreadyExistException(UserAlreadyExistException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handledIllegalArgumentException(IllegalArgumentException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new AuthResponseDTO("Unsupported role value provided"));
	}

	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handleMessagingException(MessagingException ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new AuthResponseDTO("Failed to send verification email"));
	}

	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handleInvalidVerificationExcpetion(InvalidVerificationTokenException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handleVerificationTokenExpiredException(
			VerificationTokenExpiredException ex) {

		return ResponseEntity.status(HttpStatus.GONE).body(new AuthResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handleUserNotFoundException(UserNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthResponseDTO(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<AuthResponseDTO> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler({ DisabledException.class })
	public ResponseEntity<AuthResponseDTO> handleDisabledException(DisabledException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthResponseDTO(ex.getMessage()));
	}

	@ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class, 
		InternalAuthenticationServiceException.class})
	public ResponseEntity<AuthResponseDTO> handleAuthenticationFailures(RuntimeException ex) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDTO("Invalid Email or Password"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<AuthResponseDTO> handledGeneralExcpetion(Exception ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponseDTO(ex.getMessage()));
	}
}
