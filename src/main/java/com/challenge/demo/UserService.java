package com.challenge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final WorkHourRepository workHourRepository;

    @Autowired
    public UserService(UserRepository userRepository, WorkHourRepository workHourRepository) {
        this.userRepository = userRepository;
        this.workHourRepository = workHourRepository;
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByActive(true);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
    public List<WorkHour> getWorkHoursByUserId(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) return workHourRepository.findAllByWorkHourId_User(user.get());
        else return List.of();
    }
}
