package com.example.asianfoodapp.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginDTO {
    private String login;
    private String password;
}
