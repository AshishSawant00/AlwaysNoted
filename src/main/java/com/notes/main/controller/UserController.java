package com.notes.main.controller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.main.dto.request.LogInRequestDTO;
import com.notes.main.dto.request.SignupRequestDTO;
import com.notes.main.dto.response.LoginResponseDTO;
import com.notes.main.dto.response.SignUpResponseDTO;
import com.notes.main.entity.User;
import com.notes.main.service.UserLoginService;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "always-noted")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {

	  @Autowired
	    private UserLoginService userService;

	    @PostMapping("/signup")
	    public ResponseEntity<?> signUp(@RequestBody @Valid SignupRequestDTO signUpRequestDTO) {
	    	
	        User savedUser = userService.saveDetails(signUpRequestDTO);
	        log.info("inside signup");

	        // Map the savedUser entity to a UserResponseDTO
	        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
	        signUpResponseDTO.setName(savedUser.getName());
	        signUpResponseDTO.setEmail(savedUser.getEmail());
	       
	        // Map other fields as needed

	        // Return the response with the UserResponseDTO and HTTP status CREATED
	        return new ResponseEntity<>(signUpResponseDTO, HttpStatus.CREATED);
	    }
	    
	    
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LogInRequestDTO loginRequestDTO) {
	        User user = userService.login(loginRequestDTO);
	        log.trace("Inside login method");

	        if (user != null) {
	            // User successfully logged in
	        	LoginResponseDTO dto = new LoginResponseDTO();
	        	dto.setUuid(user.getUuid());
	        	dto.setName(user.getName());
	        	dto.setId(user.getId());
	   
	            return ResponseEntity.ok(dto);
	        } else {
	            // Login failed
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
	        }
	    }

}
