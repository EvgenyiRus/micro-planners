package com.example.micro.planner.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.example.micro.planner"})
@EnableJpaRepositories(basePackages = {"com.example.micro.planner.users"})
@RefreshScope
public class PlannerUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerUsersApplication.class, args);
    }
}
