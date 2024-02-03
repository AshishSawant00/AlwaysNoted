package com.notes.main.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.notes.main.dto.request.NotesRequestDTO;
import com.notes.main.dto.request.PaginationRequest;
import com.notes.main.dto.response.NotesResponseDTO;
import com.notes.main.entity.Notes;

@Service
public interface NotesService {

	List<NotesResponseDTO> notes(int id);

	public void saveNotes(NotesRequestDTO dto);

	public Notes findById(int id);

	public void saveNoteEntity(Notes notes);

	public String deleteNote(int id);

	List<NotesResponseDTO> feed();

	public Page<NotesResponseDTO> getNotes(PaginationRequest paginationRequest, int loggedInUserId);

}
