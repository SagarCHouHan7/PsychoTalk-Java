package com.psychotalk.exception.customExceptions;

public class AppointmentStatusViolationException extends RuntimeException {
    public AppointmentStatusViolationException(){
        super("Trying to set status which is not allowed");
    }
    public AppointmentStatusViolationException(String message) {
        super(message);
    }
}
