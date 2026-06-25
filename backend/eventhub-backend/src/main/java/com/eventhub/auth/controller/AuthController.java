package com.eventhub.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventhub.auth.dto.AuthResponeDTO;
import com.eventhub.auth.dto.RegisterDTO;
import com.eventhub.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponeDTO> register (@Valid @RequestBody RegisterDTO dto,BindingResult result) {
		
		AuthResponeDTO response = service.findUserByEmail(dto);
	
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
