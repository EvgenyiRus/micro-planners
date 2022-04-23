package com.example.micro.planner.users.mq.func;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Сервис для работы с каналами обмена сообщениями
 */

@Service
@Getter
@Slf4j
public class MessageFuncActions {

    // канал для сообщений
    MessageFunc messageFunc;

    public MessageFuncActions(MessageFunc messageFunc) {
        this.messageFunc = messageFunc;
    }

    // Добавление в канал нового сообщения
    public void sendNewUserMessage(Long id) {
        Message<Long> message = MessageBuilder.withPayload(id).build();
        messageFunc.getOutputChannel().emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
        log.info("Message sent: " + id);
    }
}
