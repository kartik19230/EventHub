package com.eventhub.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class SecurityContextService {

	
	public void establishAuthenticationSession(Authentication authentication,
			HttpServletRequest request) {
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		
		context.setAuthentication(authentication);
		
		HttpSession session = request.getSession(true);

		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
				, context);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}
	
}
