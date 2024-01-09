package com.notes.main.dto;

import org.springframework.data.jpa.domain.Specification;

import com.notes.main.entity.Notes;

public class NoteSpecifications {

    public static Specification<Notes> titleOrContentLike(String searchTerm) {
        return (root, query, builder) -> builder.or(
                builder.like(root.get("title"), "%" + searchTerm + "%"),
                builder.like(root.get("content"), "%" + searchTerm + "%")
        );
    }
}
