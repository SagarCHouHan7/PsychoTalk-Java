package com.psychotalk.exception.customExceptions;

public class ExpertNotFoundException extends RuntimeException{
    public ExpertNotFoundException(){
        this("Expert not found");
    }
    public ExpertNotFoundException(String msg){
        super(msg);
    }
}
