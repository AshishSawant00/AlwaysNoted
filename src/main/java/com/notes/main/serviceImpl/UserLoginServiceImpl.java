package com.notes.main.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.notes.main.dto.request.LogInRequestDTO;
import com.notes.main.dto.request.SignupRequestDTO;
import com.notes.main.entity.User;
import com.notes.main.repository.userRepository;
import com.notes.main.service.UserLoginService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private userRepository repository;
	
	
	@Override
	public User saveDetails(SignupRequestDTO dto) {
		
		log.info("Inside saveDetails"+dto);

		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setUserName(dto.getUserName());
		user.setName(dto.getName());
		user.setUuid(UUID.randomUUID().toString());
		
		return repository.save(user);
		
	}
	
	
	 public User login(LogInRequestDTO loginRequestDTO) {
	        String userName = loginRequestDTO.getUsername();
	        String password = loginRequestDTO.getPassword();
	        log.info("Inside login service");

	        User user = null;
			try {
				user = repository.findByUserName(userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        if (user != null && user.getPassword().equals(password)) {
	            // User found and password matches
	            return user;
	        }

	        // User not found or password does not match
	        return null;
	    }

}
