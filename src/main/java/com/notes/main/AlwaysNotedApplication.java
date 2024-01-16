package com.notes.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AlwaysNotedApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AlwaysNotedApplication.class, args);
		System.out.println("Starting Application!!!!!! ");
	}

}
