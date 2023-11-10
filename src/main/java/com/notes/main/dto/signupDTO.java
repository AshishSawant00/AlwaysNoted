package com.notes.main.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class signupDTO {
	
	private String userName;
	private String password;
	private String email;

}
