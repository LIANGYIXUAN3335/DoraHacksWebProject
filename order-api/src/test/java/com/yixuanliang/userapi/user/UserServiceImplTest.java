package com.yixuanliang.userapi.user;

import com.yixuanliang.userapi.rest.dto.UpdateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setNickname("User");
        user.setEmail("user@example.com");
        user.setRole("ROLE_USER");
    }

    @Test
    void testGetUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.getUsers();

        assertEquals(1, users.size());
        assertEquals(user.getUsername(), users.get(0).getUsername());
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByUsername("user");

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void testHasUserWithUsername() {
        when(userRepository.existsByUsername("user")).thenReturn(true);

        boolean exists = userService.hasUserWithUsername("user");

        assertTrue(exists);
    }

    @Test
    void testHasUserWithEmail() {
        when(userRepository.existsByEmail("user@example.com")).thenReturn(true);

        boolean exists = userService.hasUserWithEmail("user@example.com");

        assertTrue(exists);
    }

    @Test
    void testValidateAndGetUserByUsername() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        User foundUser = userService.validateAndGetUserByUsername("user");

        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testUpdateUser() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("newNickname", "newEmail@example.com", "newProfilePicture");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser("user", updateUserRequest);

        assertEquals("newNickname", updatedUser.getNickname());
        assertEquals("newEmail@example.com", updatedUser.getEmail());
        assertEquals("newProfilePicture", updatedUser.getProfilePicture());
    }
}