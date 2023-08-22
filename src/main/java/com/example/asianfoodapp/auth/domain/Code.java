package com.example.asianfoodapp.auth.domain;

public enum Code {
    SUCCESS("Operation end success"),
    NOT_FOUND("User not found"),
    WRONG_PASSWORD("Wrong password"),
    PERMIT("Access permit"),
    TOKEN_NOT_VALID("Provided token is not valid");

    public final String message;
    private Code(String message) {
        this.message = message;
    }
}
