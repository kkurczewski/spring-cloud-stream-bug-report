package com.example;

import java.util.function.Consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    private final StreamBridge streamBridge;

    public Application(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Consumer<String> alerts() {
        return System.err::println;
    }

    @PostMapping("/notify")
    public void pushNotification(@RequestBody String payload) {
        streamBridge.send("notifications", payload);
    }
}
