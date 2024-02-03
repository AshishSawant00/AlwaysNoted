package com.notes.main.service.CustomValidation;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.notes.main.repository.userRepository;


public class UniqueUserNameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private userRepository userRepository; // Assume UserRepository is your JPA repository

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // Perform validation logic, check if the username is unique in the database
        return username != null && !userRepository.existsByUserName(username);
    }
}
