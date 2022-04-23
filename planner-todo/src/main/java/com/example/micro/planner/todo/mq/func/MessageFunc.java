package com.example.micro.planner.todo.mq.func;

import com.example.micro.planner.todo.service.TestDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

/**
 * Описание канала Spring Cloud Stream, принимающий сообщения
 */

@Configuration
public class MessageFunc {

    private final TestDataService testDataService;

    public MessageFunc(TestDataService testDataService) {
        this.testDataService = testDataService;
    }

    @Bean
    public Consumer<Message<Long>> newUserActionConsume() {
        return message -> testDataService.init(message.getPayload()); // getPayload() возвращает id
    }
}
