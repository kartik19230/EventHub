package com.eventhub.auth.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.eventhub.auth.event.UserRegistrationEvent;
import com.eventhub.auth.service.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegistrationEventListener {

	private final EmailService emailService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleUserRegisterEvent(UserRegistrationEvent event) {

		emailService.sendVerificationMail(event.email(), event.username(), event.verificationToken());
	}

}
