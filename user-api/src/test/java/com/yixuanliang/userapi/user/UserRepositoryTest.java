package com.yixuanliang.userapi.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setNickname("User");
        user.setEmail("user@example.com");
        user.setRole("USER");
        userRepository.save(user);
    }

    @Test
    void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("user");
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void testExistsByUsername() {
        boolean exists = userRepository.existsByUsername("user");
        assertTrue(exists);
    }

    @Test
    void testExistsByEmail() {
        boolean exists = userRepository.existsByEmail("user@example.com");
        assertTrue(exists);
    }
}