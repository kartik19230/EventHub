package com.eventhub.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI eventHubOpenAPI() {
		
		return new OpenAPI()
				.info(new Info()
						.title("EventHub API")
						.description("Production oriented Event management backend built using Spring Boot")
						.version("v1.0.0")
						.contact(new Contact()
								.name("Kartik Shinde")
								.email("kartikkshinde0103@gmail.com"))
						.license(new License()
								.name("MIT License")))
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new Components()
						.addSecuritySchemes("Bearer Authentication", 
								new SecurityScheme()
								.name("Bearer Authentication")
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));
		
	}
}
