package com.notes.main.dto.request;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;

import com.notes.main.service.CustomValidation.UniqueUsername;

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
	@UniqueUsername
	private String userName;
	@Email
	private String email;
	private String password;
}
