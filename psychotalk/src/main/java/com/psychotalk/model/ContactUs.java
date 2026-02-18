package com.psychotalk.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContactUs {
    String message;
    LocalDateTime dateTime;
    String senderName;
    String subject;
    String emailAddress;
}
