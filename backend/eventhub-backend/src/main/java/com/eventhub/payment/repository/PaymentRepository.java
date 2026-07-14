package com.eventhub.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventhub.payment.entity.Payment;
import java.util.Optional;

import com.eventhub.registration.entity.EventRegistration;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Optional<Payment> findByRegistration(EventRegistration registration);
	
	boolean existsByRegistration(EventRegistration registration);
}
