package com.psychotalk.exception.customExceptions;

public class AppointmentNotFoundException extends RuntimeException{
    public AppointmentNotFoundException(){
        super("Appointment doesn't exists");
    }
    public AppointmentNotFoundException(String msg){
        super(msg);
    }
}
