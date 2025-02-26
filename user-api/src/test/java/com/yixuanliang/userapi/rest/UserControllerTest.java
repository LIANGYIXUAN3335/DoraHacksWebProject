package com.yixuanliang.userapi.rest;

import com.yixuanliang.userapi.user.User;
import com.yixuanliang.userapi.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private PublicController publicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNumberOfUsers() {
        when(userService.getUsers()).thenReturn(Collections.emptyList());

        Integer numberOfUsers = publicController.getNumberOfUsers();

        assertEquals(0, numberOfUsers);
    }

    @Test
    void testGetNumberOfUsersWithUsers() {
        when(userService.getUsers()).thenReturn(Collections.singletonList(new User()));

        Integer numberOfUsers = publicController.getNumberOfUsers();

        assertEquals(1, numberOfUsers);
    }
}