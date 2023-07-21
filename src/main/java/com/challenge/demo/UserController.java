package com.challenge.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getActiveUsers(){
        return userService.getAllActiveUsers();
    }

    @GetMapping("{userId}/worked_hours")
    public List<WorkHour> getWorkHoursByUser(@PathVariable("userId") Integer id) {

        return userService.getWorkHoursByUserId(id);
    }

}
