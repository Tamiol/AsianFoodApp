package com.example.asianfoodapp.auth.domain.dto;

import com.example.asianfoodapp.auth.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class UserRegisterDTO {

    @NotNull
    @Length(min = 3, max = 20, message = "Login should have from 3 to 20 characters")
    private String login;
    @Email
    private String email;
    @NotNull
    @Length(min = 8, max = 50, message = "Password should have from 8 to 50 characters")
    private String password;
    private Role role;
}
