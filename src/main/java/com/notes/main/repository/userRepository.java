package com.notes.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.main.entity.User;

public interface userRepository extends JpaRepository<User, Integer> {
	
	 User findByUserName(String userName);
	 
	 User findByUuid(String uuid);

}
