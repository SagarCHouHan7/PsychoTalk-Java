package com.psychotalk.controller.publicControllers;

import com.psychotalk.model.Email;
import com.psychotalk.service.publicService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/contact")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/mail")
    public ResponseEntity<?> sendMailToPsychoTalk(@RequestBody Email email){
         emailService.sendEmail(email);
        return ResponseEntity.ok("status : success");
    }
}
