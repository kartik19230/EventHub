package com.eventhub.payment.service.impl;

import com.eventhub.payment.dto.request.PaymentRequest;
import com.eventhub.payment.dto.response.PaymentResponse;
import com.eventhub.payment.service.PaymentService;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@Server
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
	
	@Override
	public PaymentResponse payForRegistration(Long registrationId, PaymentRequest request) {
		
		return null;
	}

}
