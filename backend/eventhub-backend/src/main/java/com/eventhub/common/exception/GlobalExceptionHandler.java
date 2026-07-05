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

import com.eventhub.auth.dto.MessageResponse;
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
	public ResponseEntity<MessageResponse> handledUserAlreadyExistException(UserAlreadyExistException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handledIllegalArgumentException(IllegalArgumentException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new MessageResponse("Unsupported role value provided"));
	}

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleMessagingException(MessagingException ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse("Failed to send verification email"));
	}

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleInvalidVerificationExcpetion(InvalidVerificationTokenException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleVerificationTokenExpiredException(
			VerificationTokenExpiredException ex) {

		return ResponseEntity.status(HttpStatus.GONE).body(new MessageResponse(ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleUserNotFoundException(UserNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}

	@ExceptionHandler({ DisabledException.class })
	public ResponseEntity<MessageResponse> handleDisabledException(DisabledException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Unverified User"));
	}

	@ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class, 
		InternalAuthenticationServiceException.class})
	public ResponseEntity<MessageResponse> handleAuthenticationFailures(RuntimeException ex) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid Email or Password"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageResponse> handledGeneralExcpetion(Exception ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(ex.getMessage()));
	}
}
