package com.example.asianfoodapp.auth.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("User with provided name already exists");
    }
}
