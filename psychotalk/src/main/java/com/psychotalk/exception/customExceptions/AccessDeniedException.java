package com.psychotalk.exception.customExceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(){
        this("this operation not allowed");
    }
    public AccessDeniedException(String message) {
        super(message);
    }
}
