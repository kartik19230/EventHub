package com.eventhub.auth.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	private final TemplateEngine engine;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	public void sendVerificationMail(String to, String name, String token) {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		System.out.println("Email Sending run");
		
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, 
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
		
		
		String verificationUrl = "http://localhost:8080/auth/verify?token=" + token;
		
		Context context = new Context();
		context.setVariable("name", name);
		context.setVariable("verificationUrl", verificationUrl);
		
		String htmlContent = engine.process("email-template", context);
		
		helper.setTo(to);
		helper.setSubject("Verify your email address - Action Required");
		helper.setText(htmlContent,true);
		helper.setFrom(fromEmail);
		
		mailSender.send(mimeMessage);
		
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
