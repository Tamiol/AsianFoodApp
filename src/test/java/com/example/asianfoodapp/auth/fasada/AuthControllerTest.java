package com.example.asianfoodapp.auth.fasada;

import com.example.asianfoodapp.auth.domain.AuthResponse;
import com.example.asianfoodapp.auth.domain.Role;
import com.example.asianfoodapp.auth.domain.dto.UserRegisterDTO;
import com.example.asianfoodapp.auth.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewUser() {
        //given
        UserRegisterDTO userRegisterDTO = UserRegisterDTO
                .builder()
                .login("Admin")
                .password("Admin33!")
                .role(Role.ADMIN)
                .build();

        //when
        ResponseEntity<AuthResponse> response = authController.addNewUser(userRegisterDTO);

        //then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertEquals("Operation end success", response.getBody().getMessage());
    }

    @Test
    public void tryCreateUserWithTooShortPassword() {
        //given
        UserRegisterDTO userRegisterDTO = UserRegisterDTO
                .builder()
                .login("User")
                .password("123")
                .role(Role.USER)
                .build();

        //when
        ResponseEntity<AuthResponse> response = authController.addNewUser(userRegisterDTO);

        //then

        System.out.println(response);
    }
}