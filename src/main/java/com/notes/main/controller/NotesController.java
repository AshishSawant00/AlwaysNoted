package com.notes.main.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notes.main.dto.request.NotesRequestDTO;
import com.notes.main.dto.request.PaginationRequest;
import com.notes.main.dto.response.NotesResponseDTO;
import com.notes.main.entity.Notes;
import com.notes.main.service.NotesService;
import com.notes.main.serviceImpl.PdfService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "always-noted")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class NotesController {

	@Autowired
	NotesService notesService;

	@Autowired
	private PdfService pdfService;

	@GetMapping("/{id}")
	List<NotesResponseDTO> findById(@PathVariable int id) {
		List<NotesResponseDTO> notes = notesService.notes(id);
		return notes;
	}

	@GetMapping("feed")
	List<NotesResponseDTO> feed() {
		List<NotesResponseDTO> notes = notesService.feed();

		return notes;
	}

	@PostMapping("/save-note")
	ResponseEntity<String> save(@RequestBody NotesRequestDTO dto) {
		if (!dto.getUuid().isBlank()) {
			notesService.saveNotes(dto);
			return ResponseEntity.ok("Note saved with uuid - " + dto.getUuid());
		}

		return null;
	}

	@PutMapping("/feed/{id}")
	void toFeed(@PathVariable int id, @RequestBody NotesRequestDTO dto) {
		Notes note = notesService.findById(id);
		note.setContent(dto.getContent());
		note.setTitle(dto.getTitle());
		note.setToFeed(true);
		notesService.saveNoteEntity(note);
	}

	@PutMapping("/note/{id}")
	void updateNote(@PathVariable int id, @RequestBody NotesRequestDTO dto) {
		if (!dto.getContent().isBlank()) {
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

	@PostMapping(value = "/api/notes/pdf", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> saveNotesAsPdf(@RequestBody Notes notes) {
		try {
			String noteContent = buildNoteContent(notes); // Implement a method to convert Note objects to a string
			byte[] pdfBytes = pdfService.generatePdf(noteContent);
			return ResponseEntity.ok().body(pdfBytes);
		} catch (IOException e) {
			// Handle exception appropriately
			return ResponseEntity.status(500).body(null);
		}
	}

	private String buildNoteContent(Notes notes) {

		// Implement logic to convert Note objects to a single string

		// Example: Concatenate titles and contents

		StringBuilder contentBuilder = new StringBuilder();

		{

			contentBuilder.append(notes.getTitle()).append(": ").append(notes.getContent()).append("\n");

		}

		return contentBuilder.toString();

	}

	@PostMapping("/pagination")
	private ResponseEntity<Page<NotesResponseDTO>> pagination(@RequestBody PaginationRequest paginationRequest, @RequestParam int loggedInUserId) {
		Page<NotesResponseDTO> notes = notesService.getNotes(paginationRequest, loggedInUserId);
		log.error("Inside Pagination {} ",paginationRequest.getSearch());
		return ResponseEntity.ok(notes);
	}

}
