package com.example.asianfoodapp.auth.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AuthResponse {

    private final String timestamp;
    private final String message;
    private Code code;

    public AuthResponse(Code code) {
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.message = code.message;
        this.code = code;
    }
}
