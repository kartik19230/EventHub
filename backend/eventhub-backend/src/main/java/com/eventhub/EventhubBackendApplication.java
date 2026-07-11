package com.eventhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@ConfigurationPropertiesScan
@EnableScheduling
public class EventhubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventhubBackendApplication.class, args);
	}

}
