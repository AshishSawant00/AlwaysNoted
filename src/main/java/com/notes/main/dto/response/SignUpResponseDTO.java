package com.notes.main.dto.response;

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
public class SignUpResponseDTO {

	private String name;
	private String email;
	  
	
}
