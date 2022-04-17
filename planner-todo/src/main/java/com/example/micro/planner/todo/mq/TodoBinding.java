package com.example.micro.planner.todo.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

// создание необходимых каналов для работы с mq, каналы нужны для обмена сообщениями(принимает)
public interface TodoBinding {

    String INPUT_CHANNEL = "todoInputChannel";

    @Input(INPUT_CHANNEL)
    MessageChannel todoOutputChannel();
}
