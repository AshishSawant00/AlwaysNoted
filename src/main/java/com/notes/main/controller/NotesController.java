package com.notes.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.main.dto.request.NotesRequestDTO;
import com.notes.main.dto.response.NotesResponseDTO;
import com.notes.main.entity.Notes;
import com.notes.main.service.NotesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "always-noted")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class NotesController {

	@Autowired
	NotesService notesService;

	@GetMapping("/{id}")
	List<NotesResponseDTO> findById(@PathVariable int id) {
		List<NotesResponseDTO> notes = notesService.notes(id);
		return notes;
	}

	@PostMapping("/save-note")
	ResponseEntity<String> save(@RequestBody NotesRequestDTO dto) {
		if (!dto.getUuid().isBlank()) {
			notesService.saveNotes(dto);
			return ResponseEntity.ok("Note saved with uuid - "+dto.getUuid());
		}

		return null;
	}
	
	@PutMapping("/note/{id}")
	void updateNote(@PathVariable int id, @RequestBody NotesRequestDTO dto){
		if(!dto.getContent().isBlank()) {
			Notes note = notesService.findById(id);
			note.setContent(dto.getContent());
			note.setTitle(dto.getTitle());
			notesService.saveNoteEntity(note);
		}
	}
	
	@DeleteMapping("/note/{id}")
	String deleteNote(@PathVariable(name = "id") int id) {
		String deleteNote = notesService.deleteNote(id);
		return deleteNote;
	}
	

}
