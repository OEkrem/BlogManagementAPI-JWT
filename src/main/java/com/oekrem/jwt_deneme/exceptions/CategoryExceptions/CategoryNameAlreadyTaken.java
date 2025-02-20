package com.oekrem.jwt_deneme.exceptions.CategoryExceptions;

public class CategoryNameAlreadyTaken extends RuntimeException{
    public CategoryNameAlreadyTaken() {
        super("Category Name Already Taken");
    }
    public CategoryNameAlreadyTaken(String message) {
        super(message);
    }
}
