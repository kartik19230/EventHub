package com.eventhub.payment.service;

import com.eventhub.payment.dto.request.PaymentRequest;
import com.eventhub.payment.dto.response.PaymentResponse;

public interface PaymentService {

	PaymentResponse payForRegistration(Integer registrationId,PaymentRequest request);
}
