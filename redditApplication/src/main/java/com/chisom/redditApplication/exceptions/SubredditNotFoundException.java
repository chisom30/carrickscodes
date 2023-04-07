package com.chisom.redditApplication.exceptions;

public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String exMessage) {
        super(exMessage);
    }
}
