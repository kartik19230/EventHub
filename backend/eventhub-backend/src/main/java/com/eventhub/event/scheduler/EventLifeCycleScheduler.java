package com.eventhub.event.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eventhub.event.entity.Event;
import com.eventhub.event.enums.EventStatus;
import com.eventhub.event.repository.EventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventLifeCycleScheduler {

	private final EventRepository eventRepository;

	@Scheduled(fixedRate = 30000)
	public void openRegistrations() {

		LocalDateTime now = LocalDateTime.now();

		List<Event> eligibleEvents = eventRepository
				.findByStatusAndRegistrationOpenAtLessThanEqual(EventStatus.PUBLISHED, now);

		if (eligibleEvents.isEmpty()) {
			return;
		}

		log.info("Cron Job: Found {} Events for registration opening", eligibleEvents.size());

		for (Event event : eligibleEvents) {
			event.setStatus(EventStatus.REGISTRATION_OPEN);
			log.info("Event ID [{}] ('{}') is now open for registration.", event.getId(), event.getTitle());
		}

		eventRepository.saveAll(eligibleEvents);
	}

	@Scheduled(fixedRate = 30000)
	public void closeRegistration() {

		LocalDateTime now = LocalDateTime.now();

		List<Event> eligibleEvents = eventRepository
				.findByStatusAndRegistrationCloseAtLessThanEqual(EventStatus.REGISTRATION_OPEN, now);

		if (eligibleEvents.isEmpty()) {
			return;
		}

		log.info("Cron Job: Found {} Events for registration closing", eligibleEvents.size());

		for (Event event : eligibleEvents) {
			event.setStatus(EventStatus.REGISTRATION_CLOSED);
			log.info("Event ID [{}] ('{}') is now closing the registration.", event.getId(), event.getTitle());
		}

		eventRepository.saveAll(eligibleEvents);

	}
	
	@Scheduled(fixedRate = 30000)
	public void eventStarts() {

		LocalDateTime now = LocalDateTime.now();

		List<Event> eligibleEvents = eventRepository
				.findByStatusAndStartDateTimeLessThanEqual(EventStatus.REGISTRATION_CLOSED, now);

		if (eligibleEvents.isEmpty()) {
			return;
		}

		log.info("Cron Job: Found {} Events which are starting", eligibleEvents.size());

		for (Event event : eligibleEvents) {
			event.setStatus(EventStatus.ONGOING);
			log.info("Event ID [{}] ('{}') is starting.", event.getId(), event.getTitle());
		}

		eventRepository.saveAll(eligibleEvents);

	}
	
	@Scheduled(fixedRate = 30000)
	public void eventEnds() {

		LocalDateTime now = LocalDateTime.now();

		List<Event> eligibleEvents = eventRepository
				.findByStatusAndEndDateTimeLessThanEqual(EventStatus.ONGOING, now);

		if (eligibleEvents.isEmpty()) {
			return;
		}

		log.info("Cron Job: Found {} Events which are ending", eligibleEvents.size());

		for (Event event : eligibleEvents) {
			event.setStatus(EventStatus.COMPLETED);
			log.info("Event ID [{}] ('{}') is ending.", event.getId(), event.getTitle());
		}

		eventRepository.saveAll(eligibleEvents);

	}
	
	
	
}
