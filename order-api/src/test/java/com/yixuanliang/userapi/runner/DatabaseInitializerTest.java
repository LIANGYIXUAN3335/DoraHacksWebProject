package com.yixuanliang.userapi.runner;

import com.yixuanliang.userapi.security.SecurityConfig;
import com.yixuanliang.userapi.user.User;
import com.yixuanliang.userapi.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class DatabaseInitializerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DatabaseInitializer databaseInitializer;

    private static final List<User> USERS = Arrays.asList(
            new User("admin", "admin", "Admin", "admin@mycompany.com", SecurityConfig.ADMIN),
            new User("user", "user", "User", "user@mycompany.com", SecurityConfig.USER)
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRunWhenUsersExist() {
        when(userService.getUsers()).thenReturn(USERS);

        databaseInitializer.run();

        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    void testRunWhenNoUsersExist() {
        when(userService.getUsers()).thenReturn(Collections.emptyList());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        databaseInitializer.run();

        verify(userService, times(2)).saveUser(any(User.class));
        verify(passwordEncoder, times(2)).encode(anyString());
    }
}