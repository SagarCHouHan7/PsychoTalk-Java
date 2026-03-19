package com.psychotalk.exception.customExceptions;

public class MailNotSentException extends RuntimeException{
    public MailNotSentException(){
        this("Mail not sent");
    }
    public MailNotSentException(String msg){
        super(msg);
    }
}
