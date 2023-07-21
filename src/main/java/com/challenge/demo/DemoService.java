package com.challenge.demo;

import com.challenge.demo.dto.UserDTO;
import com.challenge.demo.dto.WorkHourDTO;
import com.challenge.demo.exception.InvalidRequestException;
import com.challenge.demo.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemoService {
    private final UserRepository userRepository;
    private final WorkHourRepository workHourRepository;

    @Autowired
    public DemoService(UserRepository userRepository, WorkHourRepository workHourRepository) {
        this.userRepository = userRepository;
        this.workHourRepository = workHourRepository;
    }

    public List<UserDTO> getAllActiveUsers() {
        List<User> activeUsers = userRepository.findByActive(true);
        return activeUsers.stream().map(u -> new UserDTO(u)).toList();
    }

    public List<WorkHourDTO> getWorkHoursByUserId(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<WorkHour> workHours = workHourRepository.findByUser(user.get());
            return workHours.stream().map(w -> new WorkHourDTO(w)).toList();
        }
        else return List.of();
    }

    public void createWorkHour(WorkHourDTO workHourDTO) throws InvalidRequestException {
        if (workHourDTO.getHours() <= 0 || workHourDTO.getHours() > 24) {
            throw new InvalidRequestException(InvalidRequestException.INVALID_HOUR);
        }

        Optional<User> user = userRepository.findById(workHourDTO.getId());
        if (user.isPresent()) {
            WorkHourId id = new WorkHourId(user.get(), workHourDTO.getDate());
            Optional<WorkHour> workHour = workHourRepository.findById(id);

            if (workHour.isPresent()) throw new InvalidRequestException("work hour for user id=" + workHourDTO.getId() + " and date=" + workHourDTO.getDate() + " already exists");

            WorkHour wh = new WorkHour(
                    user.get(),
                    workHourDTO.getDate(),
                    workHourDTO.getHours());
            workHourRepository.save(wh);
        }
        else throw new InvalidRequestException("user with id=" + workHourDTO.getId() + " does not exist");
    }
}
