package com.example.micro.planner.todo.feign;

import com.google.common.io.CharStreams;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

@Component
@Slf4j
public class FeignExceptionHandler implements ErrorDecoder {

    // вызывается каждый раз при ошибке вызова через Feign
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 204: {
                log.warn("Ошибка 204");
                return new ResponseStatusException(HttpStatus.NO_CONTENT, readMessage(response));
            }
            case 406: {
                log.warn("Ошибка 406");
                return new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, readMessage(response));
            }
            case 503: {
                log.warn("Ошибка 503");
                return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, readMessage(response));
            }
        }
        return null;
    }

    // получить текст ошибки в формате String из потока
    private String readMessage(Response response) {
        String message = null;
        Reader reader = null;
        try {
            reader = response.body().asReader(Charset.defaultCharset());
            message = CharStreams.toString(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
