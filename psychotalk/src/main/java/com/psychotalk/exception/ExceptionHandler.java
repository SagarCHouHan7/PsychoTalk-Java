package com.psychotalk.exception;

import com.psychotalk.exception.customExceptions.MailNotSentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MailNotSentException.class)
    public ResponseEntity<ExceptionResponse> HandleMailNotSentException(MailNotSentException ex){
        String msg = ex.getMessage();
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .success(true)
                .message(msg)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
