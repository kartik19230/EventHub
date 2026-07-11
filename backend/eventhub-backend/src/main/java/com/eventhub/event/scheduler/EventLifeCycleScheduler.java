package com.eventhub.event.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eventhub.event.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class EventLifeCycleScheduler {

	private final EventRepository eventRepository;
	
	private static Logger log = LoggerFactory.getLogger(EventLifeCycleScheduler.class);
	
	@Scheduled(fixedRate = 60000)
	public void openRegistrations() {
		
	}
}
