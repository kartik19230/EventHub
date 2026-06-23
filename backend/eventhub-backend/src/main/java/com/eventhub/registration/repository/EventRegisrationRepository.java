package com.eventhub.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.registration.entity.EventRegistration;

public interface EventRegisrationRepository extends JpaRepository<EventRegistration, Integer> {

}
