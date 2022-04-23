package com.example.micro.planner.users.mq.func;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

import java.util.function.Supplier;

/**
 * Описание канала Spring Cloud Stream, отправляющий сообщения
 */

@Configuration
@Getter
public class MessageFunc {

    /* Внутренняя шина, из которой будут отправляться сооб. в канал SCS по требованию
     * Объект, который уведомляет всех подписчиков, что появилось новое сообщение у flux
     * Many - описывает количество подписчиков, false - канал автоматически не закрывается
     */
    Sinks.Many<Message<Long>> outputChannel =
            Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);

    /* Создание объекта для работы с каналом output(отправитель)
     * Flux - объект, хранящий сообщения. Позволяет отправлять их по требованию, а не раз в 1 сек.
     * Чтобы их считать, нужно подписаться на него
     */
    @Bean
    public Supplier<Flux<Message<Long>>> newUserActionProduce() {
        return () -> outputChannel.asFlux();
    }
}
