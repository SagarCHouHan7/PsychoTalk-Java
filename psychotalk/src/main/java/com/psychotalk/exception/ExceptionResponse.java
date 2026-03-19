package com.psychotalk.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
    private Timestamp timestamp;
}
