package com.eventhub.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eventhub.user.entity.User;
import com.eventhub.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService{

	private final UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) {

	    User dbUser = repo.findByEmail(email)
	            .orElseThrow(() ->
	                new UsernameNotFoundException("User not found"));

	    return new CustomerUserDetails(dbUser);
	}
}
