package com.chisom.redditApplication.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String exMessage){
        super(exMessage);
    }
}
