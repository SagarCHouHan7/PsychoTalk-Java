package com.psychotalk.controller;

import com.psychotalk.service.AIservices.QuickChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/user")
public class ChatController {

    @Autowired
    private QuickChatService quickChatService;

    @PostMapping("/quickChat")
    public ResponseEntity<Flux<String>> getResponse(@RequestBody String message){
        return ResponseEntity.ok(quickChatService.getNextResponse(message));
    }

    @DeleteMapping("/quickChat/reset")
    public ResponseEntity<Boolean> reset() {
        return ResponseEntity.ok(quickChatService.reset());
    }
}
