package com.chisom.redditApplication.exceptions;

public class PostNotFoundException extends RuntimeException{
   public PostNotFoundException (String exMessage){
       super(exMessage);
   }
}
