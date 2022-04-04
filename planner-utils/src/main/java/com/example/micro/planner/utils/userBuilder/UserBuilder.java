package com.example.micro.planner.utils.userBuilder;

import org.springframework.stereotype.Component;

@Component
public interface UserBuilder {
    boolean userExists(Long userId);
}