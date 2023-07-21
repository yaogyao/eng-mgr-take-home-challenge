package com.challenge.demo;

import com.challenge.demo.dto.UserDTO;
import com.challenge.demo.dto.WorkHourDTO;
import com.challenge.demo.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class DemoController {
    private final DemoService demoService;

    @Autowired
    public DemoController(DemoService userService) {
        this.demoService = userService;
    }

    @GetMapping
    public List<UserDTO> getActiveUsers(){
        return demoService.getAllActiveUsers();
    }

    @GetMapping("{userId}/worked_hours")
    public List<WorkHourDTO> getWorkHoursByUser(@PathVariable("userId") Integer id) {
        return demoService.getWorkHoursByUserId(id);
    }

    @PostMapping("{userId}/worked_hours")
    public ResponseEntity<String> createWorkHour(@PathVariable("userId") Integer id, @RequestBody WorkHourDTO workHourDTO) {
        // reuse the WorkHourDTO
        // if user set id in the RequestBody, it will be overridden
        workHourDTO.setId(id);

        try {
            demoService.createWorkHour(workHourDTO);
            return ResponseEntity.ok("");
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
