package com.example.asianfoodapp.auth.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException{

    public UserEmailAlreadyExistsException() {
        super("User with provided email already exists");
    }
}
