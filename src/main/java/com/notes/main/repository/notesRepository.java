package com.notes.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.notes.main.dto.response.NotesResponseDTO;
import com.notes.main.entity.Notes;

public interface notesRepository extends JpaRepository<Notes, Integer>{
	
	
	    List<Notes> findNotesByUserId(int userId);
	    
	    List<Notes> findAllByToFeedIsTrue();
	    
	 

}
