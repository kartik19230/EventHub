package com.eventhub.payment.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eventhub.common.security.CurrentUserService;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.payment.dto.request.PaymentRequest;
import com.eventhub.payment.dto.response.PaymentResponse;
import com.eventhub.payment.entity.Payment;
import com.eventhub.payment.enums.PaymentStatus;
import com.eventhub.payment.exception.PaymentAlreadyCompletedException;
import com.eventhub.payment.exception.RegistrationCancelledException;
import com.eventhub.payment.exception.RegistrationNotFoundException;
import com.eventhub.payment.exception.UnauthorizedPaymentAccessException;
import com.eventhub.payment.mapper.PaymentMapper;
import com.eventhub.payment.repository.PaymentRepository;
import com.eventhub.payment.service.PaymentService;
import com.eventhub.registration.entity.EventRegistration;
import com.eventhub.registration.enums.RegistrationStatus;
import com.eventhub.registration.exception.RegistrationClosedException;
import com.eventhub.registration.repository.EventRegistrationRepository;
import com.eventhub.ticket.service.TicketService;
import com.eventhub.user.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private final EventRegistrationRepository eventRegistrationRepository;
	private final PaymentRepository paymentRepository;
	private final CurrentUserService currentUserService;
	private final TicketService ticketService;
	private final PaymentMapper paymentMapper;

	@Override
	public PaymentResponse payForRegistration(Integer registrationId, PaymentRequest request) {

		EventRegistration registration = eventRegistrationRepository.findById(registrationId)
				.orElseThrow(() -> new RegistrationNotFoundException("Not registered"));
		
		User user = currentUserService.getCurrentuser();
		
		if (!registration.getUser().getId().equals(user.getId())) {
			
			throw new UnauthorizedPaymentAccessException("Unauthorized Event Access");
		}
		
		if (registration.getStatus() == RegistrationStatus.CANCELED) {
			
			throw new RegistrationCancelledException("Registration is already cancelled");
		}
		
		if (registration.getEvent().getStatus() != EventStatus.REGISTRATION_OPEN) {
			
			throw new RegistrationClosedException("Registration Closed");
		}

		Optional<Payment> paymentOpt = paymentRepository.findByRegistration(registration);

		Payment payment;

		if (paymentOpt.isPresent()) {

			payment = paymentOpt.get();

			if (payment.getStatus() == PaymentStatus.SUCCESS) {
				throw new PaymentAlreadyCompletedException("Payment already completed.");
			}

		} else {

			payment = new Payment();
			payment.setRegistration(registration);
			payment.setAmount(registration.getEvent().getPrice());

		}

		payment.setPaymentMethod(request.paymentMethod());
		payment.setTransactionId(UUID.randomUUID().toString());

		if (request.simulateSuccess()) {

			payment.setStatus(PaymentStatus.SUCCESS);
			payment.setPaidAt(LocalDateTime.now());

		} else {

			payment.setStatus(PaymentStatus.FAILED);

		}

		Payment savedPayment = paymentRepository.save(payment);

		if (savedPayment.getStatus() == PaymentStatus.SUCCESS) {
			//ticketService.issueTicket(registration);
		}

		return paymentMapper.paymentResponse(savedPayment);
	}

}
