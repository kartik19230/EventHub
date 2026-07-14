package com.eventhub.payment.dto.response;

import com.eventhub.payment.enums.PaymentMethod;
import com.eventhub.payment.enums.PaymentStatus;

import lombok.Builder;

@Builder
public record PaymentResponse (
		
		Long paymentId,
		Integer registrationId,
		PaymentStatus status,
		PaymentMethod method,
		String transactionId,
		String message){

}
