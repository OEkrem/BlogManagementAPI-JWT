package com.oekrem.jwt_deneme.exceptions.UserExceptions;

public class EmailAlreadyTakenException extends RuntimeException{
    public EmailAlreadyTakenException() {
        super();
    }
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
