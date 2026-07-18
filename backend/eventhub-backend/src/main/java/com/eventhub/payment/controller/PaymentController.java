package com.eventhub.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.payment.dto.request.PaymentRequest;
import com.eventhub.payment.dto.response.PaymentResponse;
import com.eventhub.payment.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ATTENDEE')")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping("/{registrationId}")
	public ResponseEntity<PaymentResponse> payForRegistration(@PathVariable Integer registrationId,
			@Valid @RequestBody PaymentRequest paymentRequest){
		
		System.out.println(paymentRequest);
		PaymentResponse response = paymentService.payForRegistration(registrationId, paymentRequest);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
