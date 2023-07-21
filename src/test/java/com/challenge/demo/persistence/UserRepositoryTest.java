package com.challenge.demo.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByActive() {
        // given
        User user = new User(null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        userRepository.save(user);

        // when
        List<User> activeUsers = userRepository.findByActive(true);
        List<User> inactiveUsers = userRepository.findByActive(false);

        // then
        assertThat(activeUsers).hasSize(1);
        assertThat(inactiveUsers).isEmpty();
    }
}