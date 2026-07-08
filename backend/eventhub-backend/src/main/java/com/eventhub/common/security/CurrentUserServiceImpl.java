package com.eventhub.common.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventhub.user.entity.User;

@Service
public class CurrentUserServiceImpl implements CurrentUserService{

	@Override
	public User getCurrentuser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null 
				|| !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			
			throw new IllegalStateException("No authenticated user found");
		}
		
		User user = (User)authentication.getPrincipal();
		return user;
	}

}
