package com.challenge.demo;

import com.challenge.demo.persistence.User;
import com.challenge.demo.persistence.UserRepository;
import com.challenge.demo.persistence.WorkHour;
import com.challenge.demo.persistence.WorkHourRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkHourRepository workHourRepository;

    User user1, user2, user3;
    WorkHour wh1, wh2, wh3;

    @BeforeEach
    void setup() {
        user1 = new User(null, "firstname", "lastname", "user1@email.com", true, LocalDateTime.now());
        userRepository.save(user1);

        user2 = new User(null, "firstname", "lastname", "user2@email.com", true, LocalDateTime.now());
        userRepository.save(user2);

        user3 = new User(null, "firstname", "lastname", "user3@email.com", false, LocalDateTime.now());
        userRepository.save(user3);

        wh1 = new WorkHour(user1, LocalDate.of(2023, 7, 1), 1);
        wh2 = new WorkHour(user1, LocalDate.of(2023, 7, 2), 2);
        wh3 = new WorkHour(user2, LocalDate.of(2023, 7, 3), 3);
        workHourRepository.saveAll(List.of(wh1, wh2, wh3));
    }

    @AfterEach
    void tearDown() {
        workHourRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getActiveUsers() throws Exception {
        mockMvc.perform(get("/v1/users").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getWorkHoursByUser() throws Exception {
        mockMvc.perform(get("/v1/users/{userId}/worked_hours", user1.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/v1/users/{userId}/worked_hours", user2.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void createWorkHour() throws Exception {
        int oldCount = workHourRepository.findByUser(user1).size();
        String data = "{\"date\": \"2021-01-11\",\"hours\":5.24}";

        mockMvc.perform(
                post("/v1/users/{userId}/worked_hours", user1.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .content(data))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/users/{userId}/worked_hours", user1.getId()).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(oldCount+1)));
    }
}