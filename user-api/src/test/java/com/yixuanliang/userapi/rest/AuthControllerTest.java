package com.yixuanliang.userapi.rest;

import com.yixuanliang.userapi.rest.dto.AuthResponse;
import com.yixuanliang.userapi.rest.dto.LoginRequest;
import com.yixuanliang.userapi.rest.dto.SignUpRequest;
import com.yixuanliang.userapi.security.TokenProvider;
import com.yixuanliang.userapi.user.DuplicatedUserInfoException;
import com.yixuanliang.userapi.user.User;
import com.yixuanliang.userapi.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        String username = "user";
        String password = "password";
        String token = "sampleToken";

        LoginRequest loginRequest = new LoginRequest(username, password);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenProvider.generate(authentication)).thenReturn(token);

        AuthResponse authResponse = authController.login(loginRequest);

        assertEquals(token, authResponse.accessToken());
    }

    @Test
    void testSignUp() {
        String username = "user3";
        String password = "password";
        String name = "User3";
        String email = "user3@mycompany.com";
        String token = "sampleToken";

        SignUpRequest signUpRequest = new SignUpRequest(username, password, name, email);
        Authentication authentication = mock(Authentication.class);

        when(userService.hasUserWithUsername(username)).thenReturn(false);
        when(userService.hasUserWithEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenProvider.generate(authentication)).thenReturn(token);

        AuthResponse authResponse = authController.signUp(signUpRequest);

        assertEquals(token, authResponse.accessToken());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void testSignUpWithExistingUsername() {
        String username = "user3";
        String password = "password";
        String name = "User3";
        String email = "user3@mycompany.com";

        SignUpRequest signUpRequest = new SignUpRequest(username, password, name, email);

        when(userService.hasUserWithUsername(username)).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> {
            authController.signUp(signUpRequest);
        });

        assertEquals(String.format("Username %s already been used", username), exception.getMessage());
    }

    @Test
    void testSignUpWithExistingEmail() {
        String username = "user3";
        String password = "password";
        String name = "User3";
        String email = "user3@mycompany.com";

        SignUpRequest signUpRequest = new SignUpRequest(username, password, name, email);

        when(userService.hasUserWithUsername(username)).thenReturn(false);
        when(userService.hasUserWithEmail(email)).thenReturn(true);

        DuplicatedUserInfoException exception = assertThrows(DuplicatedUserInfoException.class, () -> {
            authController.signUp(signUpRequest);
        });

        assertEquals(String.format("Email %s already been used", email), exception.getMessage());
    }
}