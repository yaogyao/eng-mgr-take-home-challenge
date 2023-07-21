package com.challenge.demo.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class WorkHourRepositoryTest {

    @Autowired
    private WorkHourRepository workHourRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUser() {
        // given
        User user1 = new User(null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        User user2 = new User(null, "firstname", "lastname", "your@email.com", true, LocalDateTime.now());
        userRepository.save(user1);
        userRepository.save(user2);

        WorkHour wh1 = new WorkHour(user1, LocalDate.of(2023, 1, 1), 1.1);
        workHourRepository.save(wh1);
        WorkHour wh2 = new WorkHour(user1, LocalDate.of(2023, 2, 1), 2.2);
        workHourRepository.save(wh2);

        // when
        List<WorkHour> workHourList1 = workHourRepository.findByUser(user1);
        List<WorkHour> workHourList2 = workHourRepository.findByUser(user2);

        // then
        assertThat(workHourList1).hasSize(2);
        assertThat(workHourList2).isEmpty();
    }
}