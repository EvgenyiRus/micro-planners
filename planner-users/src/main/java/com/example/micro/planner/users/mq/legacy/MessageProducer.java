package com.example.micro.planner.users.mq.legacy;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

// сообщения отправляются от модуля Users к модулю to_do
//@Component
//@EnableBinding(TodoBinding.class)
public class MessageProducer {

//    TodoBinding todoBinding;
//
//    public MessageProducer(TodoBinding todoBinding) {
//        this.todoBinding = todoBinding;
//    }
//
//    // добавление сообщения в канал для прослушивания
//    public void initUserData(Long userId) {
//
//        // контейнер для данных + headers
//        Message message = MessageBuilder.withPayload(userId).build();
//        todoBinding.todoOutputChannel().send(message);
//    }
}
