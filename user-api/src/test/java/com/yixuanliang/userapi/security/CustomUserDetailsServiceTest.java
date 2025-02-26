package com.yixuanliang.userapi.security;

import com.yixuanliang.userapi.user.User;
import com.yixuanliang.userapi.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        String username = "user";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword("password");
        user.setNickname("User");
        user.setEmail("user@example.com");
        user.setRole("ROLE_USER");

        when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);

        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getNickname(), userDetails.getName());
        assertEquals(user.getEmail(), userDetails.getEmail());
        assertEquals(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())), userDetails.getAuthorities());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "user";

        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(username);
        });
    }
}