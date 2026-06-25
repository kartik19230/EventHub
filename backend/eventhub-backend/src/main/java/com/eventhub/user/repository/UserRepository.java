package com.eventhub.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.user.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
