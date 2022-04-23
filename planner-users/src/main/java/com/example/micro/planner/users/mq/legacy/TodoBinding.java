package com.example.micro.planner.users.mq.legacy;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

// создание необходимых каналов для работы с mq, каналы нужны для обмена сообщениями(отправляет)
public interface TodoBinding {

//    String OUTPUT_CHANNEL = "todoOutputChannel";
//
//    @Output(OUTPUT_CHANNEL)
//    MessageChannel todoOutputChannel();
}
