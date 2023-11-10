package com.notes.main.dto.request;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotesRequestDTO {
	
	private int id;
	private String title;
	private String content;
	private String uuid;

}
