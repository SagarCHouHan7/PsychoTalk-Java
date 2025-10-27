package com.psychotalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PsychotalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsychotalkApplication.class, args);
	}

}
