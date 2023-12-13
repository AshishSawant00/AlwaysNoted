package com.notes.main.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.main.dto.request.NotesRequestDTO;
import com.notes.main.dto.response.NotesResponseDTO;
import com.notes.main.entity.Notes;
import com.notes.main.repository.notesRepository;
import com.notes.main.repository.userRepository;
import com.notes.main.service.NotesService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotesServiceImpl implements NotesService {

	@Autowired
	notesRepository notesRepository;
	@Autowired
	userRepository userRepository;

	@Autowired
	List<NotesResponseDTO> notesResponseDTO;

	@Override
	public List<NotesResponseDTO> notes(int id) {
		notesResponseDTO.clear();

		List<Notes> allNotes = notesRepository.findNotesByUserId(id);
		allNotes.forEach(e -> {
			notesResponseDTO.add(new NotesResponseDTO(e.getId(), e.getTitle(), e.getContent()));
		});

		log.info("NotesResponseDTO - {}", notesResponseDTO);

		return notesResponseDTO;
	}

	@Override
	public void saveNotes(NotesRequestDTO dto) {
		Notes notes = new Notes();

		notes.setTitle(dto.getTitle());
		notes.setContent(dto.getContent());
		notes.setUser(userRepository.findByUuid(dto.getUuid()));

		notesRepository.save(notes);

	}

	@Override
	public Notes findById(int id) {
		return notesRepository.findById(id).get();
	}

	@Override
	public void saveNoteEntity(Notes notes) {
		notesRepository.save(notes);

	}

	@Override
	public String deleteNote(int id) {
		Notes note = notesRepository.findById(id).get();
		notesRepository.delete(note);
		 try {
		     Notes notes1 = notesRepository.findById(id).get();
		     if (notes1.getDeletedAt() != null) {
					return "Note successfully deleted.";
				}

		 }catch (NoSuchElementException e) {
			 return "Note successfully deleted.";			
		}
		 
		
		return "Note not found or deletion failed.";
	}

	@Override
	public List<NotesResponseDTO> feed() {
		notesResponseDTO.clear();

		List<Notes> allNotes = notesRepository.findAllByToFeedIsTrue();
		allNotes.forEach(e -> {
			notesResponseDTO.add(new NotesResponseDTO(e.getId(), e.getTitle(), e.getContent()));
		});

		log.info("NotesResponseDTO - {}", notesResponseDTO);

		return notesResponseDTO;
	}

}
