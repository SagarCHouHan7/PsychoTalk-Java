package com.psychotalk.exception;

import com.psychotalk.exception.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ex.printStackTrace();
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .message("Something went wrong : "+ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex){
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .success(false)
                .message(ex.getMessage())
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MailNotSentException.class)
    public ResponseEntity<ExceptionResponse> HandleMailNotSentException(MailNotSentException ex){
        String msg = ex.getMessage();
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .success(false)
                .message(msg)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExpertNotFoundException.class)
    public ResponseEntity<ExceptionResponse> HandleExpertNotFoundException(ExpertNotFoundException ex){
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> HandleUserNotFoundException(UserNotFoundException ex){
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAppointmentNotFoundException(AppointmentNotFoundException ex){
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(false)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AppointmentStatusViolationException.class)
    public ResponseEntity<ExceptionResponse> handleAppointmentStatusViolationException(AppointmentStatusViolationException ex){

        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_ACCEPTABLE)
                .success(false)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleTimeSlotNotAvailableException(TimeSlotNotAvailableException ex){
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_ACCEPTABLE)
                .success(false)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }
}
