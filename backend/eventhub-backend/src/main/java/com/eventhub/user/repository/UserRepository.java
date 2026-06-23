package com.eventhub.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	

}
