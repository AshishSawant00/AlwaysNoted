package com.notes.main.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.notes.main.dto.NoteSpecifications;
import com.notes.main.dto.request.NotesRequestDTO;
import com.notes.main.dto.request.PaginationRequest;
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

		} catch (NoSuchElementException e) {
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

	public Page<NotesResponseDTO> getNotes(PaginationRequest paginationRequest, int loggedInUserId) {
		Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(),
				Sort.by(paginationRequest.getSort()).ascending());
		Specification<Notes> specification = null;
		if(loggedInUserId>0) {
		 specification = Specification.where(
	            NoteSpecifications.userEquals(loggedInUserId));
		} else if (loggedInUserId  ==0 ) {
			specification = NoteSpecifications.toFeedEquals(true);
		}

		if (StringUtils.hasText(paginationRequest.getSearch())) {
			specification = specification.and(NoteSpecifications.titleOrContentLike(paginationRequest.getSearch()));
		}

		Page<Notes> notesEntities = notesRepository.findAll(specification, pageable);
		return notesEntities.map(this::mapToResponseDTO);
	}

	private NotesResponseDTO mapToResponseDTO(Notes noteEntity) {
		// Map entity to response DTO
		return new NotesResponseDTO(noteEntity.getId(), noteEntity.getTitle(), noteEntity.getContent());
	}

}
