package com.oekrem.jwt_deneme.exceptions.TagExceptions;

public class TagNameAlreadyTakenExcepiton extends RuntimeException {
    public TagNameAlreadyTakenExcepiton() {
        super();
    }
    public TagNameAlreadyTakenExcepiton(String message) {
        super(message);
    }
}
