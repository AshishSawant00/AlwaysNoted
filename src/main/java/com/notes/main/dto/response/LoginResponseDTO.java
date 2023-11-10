package com.notes.main.dto.response;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class LoginResponseDTO {
	private String uuid;
	private String name;
	private int id;
}
