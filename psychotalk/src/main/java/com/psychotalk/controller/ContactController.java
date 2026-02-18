//package com.psychotalk.controller;
//
//
//import com.psychotalk.model.ContactMessage;
//import com.psychotalk.service.ContactService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@RestController
//@RequestMapping("/contact")
//public class ContactController {
//
//    @Autowired
//    ContactService contactService;
//
//    @PostMapping("/sendMailtoPsychoTalk")
//    public String sendMailToPsychoTalk(@RequestBody ContactMessage message){
//        return contactService.sendEmailToPsychoTalk(message);
//
//    }
//}
