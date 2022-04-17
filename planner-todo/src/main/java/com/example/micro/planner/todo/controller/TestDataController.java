package com.example.micro.planner.todo.controller;

import com.example.micro.planner.todo.service.TestDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
public class TestDataController {

    private final TestDataService testDataService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init(@RequestBody Long userId) {
        return testDataService.init(userId);
    }
}
