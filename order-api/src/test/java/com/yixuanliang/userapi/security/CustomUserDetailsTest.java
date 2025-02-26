package com.yixuanliang.userapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void testCustomUserDetails() {
        Long id = 1L;
        String username = "user";
        String password = "password";
        String name = "User";
        String email = "user@example.com";
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(id);
        customUserDetails.setUsername(username);
        customUserDetails.setPassword(password);
        customUserDetails.setName(name);
        customUserDetails.setEmail(email);
        customUserDetails.setAuthorities(authorities);

        assertEquals(id, customUserDetails.getId());
        assertEquals(username, customUserDetails.getUsername());
        assertEquals(password, customUserDetails.getPassword());
        assertEquals(name, customUserDetails.getName());
        assertEquals(email, customUserDetails.getEmail());
        assertEquals(authorities, customUserDetails.getAuthorities());
    }

    @Test
    void testUserDetailsMethods() {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setPassword("password");

        assertTrue(customUserDetails.isAccountNonExpired());
        assertTrue(customUserDetails.isAccountNonLocked());
        assertTrue(customUserDetails.isCredentialsNonExpired());
        assertTrue(customUserDetails.isEnabled());
    }
}