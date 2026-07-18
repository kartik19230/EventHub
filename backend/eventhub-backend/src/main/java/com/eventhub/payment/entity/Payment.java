package com.eventhub.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.eventhub.payment.enums.PaymentMethod;
import com.eventhub.payment.enums.PaymentStatus;
import com.eventhub.registration.entity.EventRegistration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(nullable = false,unique = true)
	private EventRegistration registration;
	
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Column(unique = false)
	private String transactionId;
	
	private LocalDateTime paidAt;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
}
