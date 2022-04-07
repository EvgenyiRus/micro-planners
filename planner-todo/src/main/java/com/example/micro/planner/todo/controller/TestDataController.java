package com.example.micro.planner.todo.controller;

import com.example.micro.planner.entity.Category;
import com.example.micro.planner.entity.Priority;
import com.example.micro.planner.entity.Task;
import com.example.micro.planner.todo.service.CategoryService;
import com.example.micro.planner.todo.service.PriorityService;
import com.example.micro.planner.todo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
public class TestDataController {

    PriorityService priorityService;
    CategoryService categoryService;
    TaskService taskService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init(@RequestBody Long userId) {
        Category categorySport = new Category("Спорт", userId);
        Category categoryHome = new Category("Семья", userId);
        Category categoryHealth = new Category("Здоровье", userId);
        Category categoryHobby = new Category("Личное", userId);
        Category categoryWork = new Category("Работа", userId);

        categoryService.add(categorySport);
        categoryService.add(categoryHome);
        categoryService.add(categoryHealth);
        categoryService.add(categoryHobby);
        categoryService.add(categoryWork);

        Priority priorityHigh = new Priority();
        priorityHigh.setColor("#fff");
        priorityHigh.setTitle("Важный");
        priorityHigh.setUserId(userId);

        Priority priorityMid = new Priority();
        priorityMid.setColor("#ffe");
        priorityMid.setTitle("Средний");
        priorityMid.setUserId(userId);

        Priority priorityLow = new Priority();
        priorityLow.setColor("#ffe");
        priorityLow.setTitle("Низкий");
        priorityLow.setUserId(userId);

        priorityService.add(priorityHigh);
        priorityService.add(priorityMid);
        priorityService.add(priorityLow);

        LocalDate nowDate = LocalDate.now();
        LocalDateTime timeSport = LocalDateTime.of(
                nowDate.getYear(), nowDate.getMonth(), nowDate.getDayOfMonth(), 21, 0, 0);
        LocalDateTime timeWork = LocalDateTime.of(
                nowDate.getYear(), nowDate.getMonth(), nowDate.getDayOfMonth(), 9, 0, 0);
        LocalDateTime timeHome = LocalDateTime.of(
                nowDate.getYear(), nowDate.getMonth(), nowDate.getDayOfMonth(), 19, 0, 0);
        LocalDateTime timeHobby = LocalDateTime.of(
                nowDate.getYear(), nowDate.getMonth(), nowDate.getDayOfMonth(), 18, 0, 0);

        Task task = Task.builder()
                .title("Плавание")
                .completed(false)
                .taskDate(Date.from(timeSport.atZone(ZoneId.systemDefault()).toInstant()))
                .priority(priorityHigh)
                .category(categorySport)
                .userId(userId)
                .build();
        taskService.add(task);

        Task taskWork = Task.builder()
                .title("Работа")
                .completed(false)
                .taskDate(Date.from(timeWork.atZone(ZoneId.systemDefault()).toInstant()))
                .priority(priorityLow)
                .category(categoryWork)
                .userId(userId)
                .build();
        taskService.add(taskWork);

        Task taskHobby = Task.builder()
                .title("Чтение")
                .completed(false)
                .taskDate(Date.from(timeHobby.atZone(ZoneId.systemDefault()).toInstant()))
                .priority(priorityMid)
                .category(categoryHobby)
                .userId(userId)
                .build();
        taskService.add(taskHobby);

        Task taskHome = Task.builder()
                .title("Семейные дела")
                .completed(false)
                .taskDate(Date.from(timeHome.atZone(ZoneId.systemDefault()).toInstant()))
                .priority(priorityHigh)
                .category(categoryHome)
                .userId(userId)
                .build();
        taskService.add(taskHome);

        Task taskHealth = Task.builder()
                .title("Прогуляться перед сном")
                .completed(false)
                .taskDate(Date.from(timeSport.atZone(ZoneId.systemDefault()).toInstant()))
                .priority(priorityHigh)
                .category(categoryHealth)
                .userId(userId)
                .build();
        taskService.add(taskHealth);

        return ResponseEntity.ok(true);
    }
}
