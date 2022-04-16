package com.example.micro.planner.todo.feign;

import com.example.micro.planner.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFeignClientFallback implements UserFeignClient {

    // вызов метода, при недоступности сервиса /user/id
    @Override
    public ResponseEntity<User> findUserById(Long id) {
        log.warn("fallback");
        return null;
    }
}
