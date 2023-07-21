package com.challenge.demo;

import com.challenge.demo.dto.UserDTO;
import com.challenge.demo.dto.WorkHourDTO;
import com.challenge.demo.exception.InvalidRequestException;
import com.challenge.demo.persistence.User;
import com.challenge.demo.persistence.UserRepository;
import com.challenge.demo.persistence.WorkHour;
import com.challenge.demo.persistence.WorkHourRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private WorkHourRepository workHourRepository;

    private DemoService demoService;

    @BeforeEach
    void setup() {
        demoService = new DemoService(userRepository, workHourRepository);
    }

    @Test
    void getAllActiveUsers() {
        // given
        User user = new User(1, null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        UserDTO userDTO = new UserDTO(user);
        when(userRepository.findByActive(eq(true))).thenReturn(List.of(user));

        // when
        List<UserDTO> list = demoService.getAllActiveUsers();

        // then
        verify(userRepository).findByActive(eq(true));
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getId()).isEqualTo(user.getId());

    }

    @Test
    void getWorkHoursByUserId() {
        // given
        User user = new User(1, null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        // when
        demoService.getWorkHoursByUserId(user.getId());

        // then
        verify(userRepository).findById(eq(user.getId()));
        verify(workHourRepository).findByUser(eq(user));
    }

    @Test
    void getWorkHoursByNonExistingUserId() {
        // given
        User user = new User(1, null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.empty());

        // when
        List<WorkHourDTO> list = demoService.getWorkHoursByUserId(user.getId());

        // then
        verify(userRepository).findById(eq(user.getId()));
        verify(workHourRepository, never()).findByUser(any());
        assertThat(list).isEmpty();
    }

    @Test
    void createWorkHourSuccessful() throws InvalidRequestException {
        // given
        User user = new User(1, null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        WorkHourDTO workHourDTO = new WorkHourDTO(user.getId(), LocalDate.of(2023, 7, 21), 4);
        WorkHour workHour = new WorkHour(user, workHourDTO.getDate(), workHourDTO.getHours());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(workHourRepository.findById(any())).thenReturn(Optional.empty());

        // when
        demoService.createWorkHour(workHourDTO);

        //then
        verify(userRepository).findById(eq(user.getId()));
        verify(workHourRepository).findById(any());
        verify(workHourRepository).save(any());
    }

    @Test
    void createWorkHourWithInvalidHour() throws InvalidRequestException {
        // given
        WorkHourDTO workHourDTO = new WorkHourDTO(1, LocalDate.of(2023, 7, 21), 25);

        // when/then
        assertThatThrownBy(() -> demoService.createWorkHour(workHourDTO))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining(InvalidRequestException.INVALID_HOUR);

        // given
        WorkHourDTO workHourDTO2 = new WorkHourDTO(1, LocalDate.of(2023, 7, 21), -25);

        // when/then
        assertThatThrownBy(() -> demoService.createWorkHour(workHourDTO2))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining(InvalidRequestException.INVALID_HOUR);
    }

    @Test
    void createWorkHourWithNonExistingUser() throws InvalidRequestException {
        // given
        WorkHourDTO workHourDTO = new WorkHourDTO(1, LocalDate.of(2023, 7, 21), 4);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // when/then
        assertThatThrownBy(() -> demoService.createWorkHour(workHourDTO))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("user with id=" + workHourDTO.getId() + " does not exist");
    }

    @Test
    void createWorkHourWithDuplicate() throws InvalidRequestException {
        // given
        User user = new User(1, null, "firstname", "lastname", "my@email.com", true, LocalDateTime.now());
        WorkHourDTO workHourDTO = new WorkHourDTO(user.getId(), LocalDate.of(2023, 7, 21), 4);
        WorkHour workHour = new WorkHour(user, workHourDTO.getDate(), workHourDTO.getHours());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(workHourRepository.findById(any())).thenReturn(Optional.of(workHour));

        // when/then
        assertThatThrownBy(() -> demoService.createWorkHour(workHourDTO))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("work hour for user id=" + workHourDTO.getId() + " and date=" + workHourDTO.getDate() + " already exists");
        verify(userRepository).findById(eq(user.getId()));
        verify(workHourRepository).findById(any());
        verify(workHourRepository, never()).save(any());
    }
}