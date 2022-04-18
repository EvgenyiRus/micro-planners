package com.example.micro.planner.todo.mq;

import com.example.micro.planner.todo.service.TestDataService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(TodoBinding.class)
public class MessageConsumer {

    private final TestDataService testDataService;

    public MessageConsumer(TestDataService testDataService) {
        this.testDataService = testDataService;
    }

    @StreamListener(target = TodoBinding.INPUT_CHANNEL)
    public void initTestData(Long userId) throws Exception {
        throw new Exception("test dlq");
        //testDataService.init(userId);
    }
}
