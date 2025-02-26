package com.yixuanliang.userapi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.yixuanliang.userapi.rest.dto.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByUsername(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", username)));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User updateUser(String username, UpdateUserRequest updateUserRequest) {
        return userRepository.findByUsername(username).map(user -> {
            user.setNickname(updateUserRequest.nickname());
            user.setEmail(updateUserRequest.email());
            user.setProfilePicture(updateUserRequest.profilePicture());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", username)));
    }
}
