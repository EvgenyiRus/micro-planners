package com.example.micro.planner.utils.userBuilder;

import com.example.micro.planner.entity.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public interface UserBuilder {
    boolean userExists(Long userId);
    Flux<User> userExistsAsync(Long userId);
}