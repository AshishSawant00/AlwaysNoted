package com.notes.main.dto;

import org.springframework.data.jpa.domain.Specification;

import com.notes.main.entity.Notes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoteSpecifications {
	
	 public static Specification<Notes> userEquals(int userId) {
	        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
	    }
	 
	 public static Specification<Notes> toFeedEquals(boolean toFeed) {
	        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("toFeed"), toFeed);
	    }


    public static Specification<Notes> titleOrContentLike(String searchTerm) {
    log.trace("Creating query for search spec for title - {}", searchTerm);
        return (root, query, builder) -> builder.or(
                builder.like(root.get("title"), "%" + searchTerm + "%")
               // builder.like(root.get("content"), "%" + searchTerm + "%")
        );
    }
}
