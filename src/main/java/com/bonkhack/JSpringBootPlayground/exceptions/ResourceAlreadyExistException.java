package com.bonkhack.JSpringBootPlayground.exceptions;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
