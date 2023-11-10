package com.notes.main.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.notes.main.dto.request.LogInRequestDTO;
import com.notes.main.dto.request.SignupRequestDTO;
import com.notes.main.entity.User;

@Service
public interface UserLoginService {
	
	User saveDetails(SignupRequestDTO dto);
	
	public User login(LogInRequestDTO loginRequestDTO);
	
	

}
