package com.eventhub.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
