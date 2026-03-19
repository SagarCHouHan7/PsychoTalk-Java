package com.psychotalk.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Email {
    String message;
    String senderName;
    String subject;
    String emailAddress;
}
