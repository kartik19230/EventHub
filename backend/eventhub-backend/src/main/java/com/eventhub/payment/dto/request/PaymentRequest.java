package com.eventhub.payment.dto.request;

import com.eventhub.payment.enums.PaymentMethod;

import lombok.Builder;

public record PaymentRequest(

	PaymentMethod paymentMethod,
	boolean simulateSuccess
	) {}
