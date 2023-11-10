package com.notes.main.dto.request;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
	
	private String name;
	private String userName;
	@Email
	private String email;
	private String password;
}
