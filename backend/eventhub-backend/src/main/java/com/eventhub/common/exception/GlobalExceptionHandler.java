package com.eventhub.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
import com.eventhub.auth.exception.VerificationTokenExpiredException;
import com.eventhub.event.exception.CannotDeleteNonDraftEventException;
import com.eventhub.event.exception.CannotModifyNonDraftEventException;
import com.eventhub.event.exception.EventAccessDeniedException;
import com.eventhub.event.exception.EventAlreadyPublishedException;
import com.eventhub.event.exception.EventNotPendingForApprovalException;
import com.eventhub.event.exception.InvalidEventScheduleException;
import com.eventhub.event.exception.InvalidRegistrationWindowException;
import com.eventhub.registration.exception.DuplicateRegistrationException;
import com.eventhub.registration.exception.EventCapacityExceedException;
import com.eventhub.registration.exception.OrganizerCannotRegisterOwnEventException;
import com.eventhub.registration.exception.RegistrationClosedException;
import com.eventhub.registration.exception.RegistrationNotOpenException;
import com.eventhub.registration.exception.UserNotRegisteredException;

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

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Unverified User"));
	}

	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleInvalidRegistrationWindowException(InvalidRegistrationWindowException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleInvalidEventScheduleException(InvalidEventScheduleException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleCannotDeleteNonDraftEventException(CannotDeleteNonDraftEventException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Cannot delete event with non draft status, Current status = " + ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleCannotModifyNonDraftEventException(CannotModifyNonDraftEventException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Cannot modify event with non draft status, Current status = " + ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleEventAlreadyPublishedException(EventAlreadyPublishedException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> EventNotPendingForApprovalException(EventNotPendingForApprovalException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleEventAccessDeniedException(EventAccessDeniedException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleDuplicateRegistrationException(DuplicateRegistrationException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleEventCapacityExceedException(EventCapacityExceedException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleOrganizerCannotRegisterOwnEventException(OrganizerCannotRegisterOwnEventException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleRegistrationNotOpenException(RegistrationNotOpenException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleRegistrationClosedException(RegistrationClosedException ex) {

		return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<MessageResponse> handleUserNotRegisteredException(UserNotRegisteredException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
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
