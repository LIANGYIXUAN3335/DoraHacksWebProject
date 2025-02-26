package com.yixuanliang.userapi.user;

import java.util.List;
import java.util.Optional;

import com.yixuanliang.userapi.rest.dto.UpdateUserRequest;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    void deleteUser(User user);

    User updateUser( String username, UpdateUserRequest userUpdateRequest);

}
