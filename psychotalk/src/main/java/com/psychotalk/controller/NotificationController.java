package com.psychotalk.controller;

import com.psychotalk.model.Notification;
import com.psychotalk.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/get")
    public ResponseEntity<List<Notification>> getAllNotifications(){
        return ResponseEntity.ok(notificationService.getAllNotification());
    }

    @PutMapping("/notification/markAsRead/{id}")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id){

        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
}
