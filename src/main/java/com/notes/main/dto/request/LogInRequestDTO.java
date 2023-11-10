package com.notes.main.dto.request;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequestDTO {
	
	private String username;
	private String password;

}
