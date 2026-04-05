package com.psychotalk;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableAsync
@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PsychotalkApplication {
	public static void main(String[] args) {

        SpringApplication.run(PsychotalkApplication.class, args);


	}

}
