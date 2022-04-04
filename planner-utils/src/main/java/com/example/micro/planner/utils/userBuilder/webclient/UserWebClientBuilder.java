package com.example.micro.planner.utils.userBuilder.webclient;

import com.example.micro.planner.entity.User;
import com.example.micro.planner.utils.userBuilder.UserBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserWebClientBuilder implements UserBuilder {

    private static final String BASE_URL = "http://localhost:8765/planner-users/user/";

    public boolean userExists(Long userId) {
        try {
            User user = WebClient.create(BASE_URL) // https://reflectoring.io/spring-webclient/
                    .post()
                    .uri("id") // .../users/id
                    .bodyValue(userId)
                    .retrieve() // вызов микросервиса
                    .bodyToFlux(User.class) // объект, содержащий ответ. Flux позовляет считывать результат асинхронно
                    .blockFirst(); // блокировка потока до получения 1 записи. Из-за этого метод становится синхронным
            if (user != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}