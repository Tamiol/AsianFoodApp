package com.example.asianfoodapp.auth.facade;

import com.example.asianfoodapp.BaseIT;
import com.example.asianfoodapp.auth.domain.Role;
import com.example.asianfoodapp.auth.domain.dto.UserLoginDTO;
import com.example.asianfoodapp.auth.domain.dto.UserRegisterDTO;
import com.example.asianfoodapp.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.annotation.DirtiesContext;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthControllerTest extends BaseIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void UserRegisterAndLogin() throws Exception {
        //given
        var userLogin = "Admin1";
        var userPassword = "Admin33!";
        UserRegisterDTO userRegisterDTO = UserRegisterDTO
                .builder()
                .login(userLogin)
                .password(userPassword)
                .email("email@gmail.com")
                .role(Role.USER)
                .build();

        //when & then
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation end success"));
        assertThat(userRepository.findUserByLogin(userLogin)).isNotEmpty();


        //try to log in
        //given
        UserLoginDTO userLoginDTO = new UserLoginDTO(userLogin, userPassword);

        //when & then
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(userLogin))
                .andExpect(cookie().value("token", notNullValue()))
                .andExpect(cookie().value("refresh", notNullValue()));
    }
}