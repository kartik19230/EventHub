package com.eventhub.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventhub.payment.dto.response.PaymentResponse;
import com.eventhub.payment.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

	@Mapping(source = "id", target = "paymentId")
	@Mapping(source = "registration.id", target = "registrationId")
	public PaymentResponse paymentResponse(Payment payment);
}
