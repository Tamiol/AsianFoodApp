package com.example.asianfoodapp.auth.domain.dto;

import com.example.asianfoodapp.auth.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterDTO {

    private String login;
    private String email;
    private String password;
    private Role role;
}
