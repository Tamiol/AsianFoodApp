package com.example.asianfoodapp.auth.domain;

public enum Code {
    SUCCESS("Operation end success");

    public final String message;
    private Code(String message) {
        this.message = message;
    }
}
