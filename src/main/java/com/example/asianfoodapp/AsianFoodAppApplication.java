package com.example.asianfoodapp;

import com.example.asianfoodapp.auth.domain.Role;
import com.example.asianfoodapp.auth.repository.UserRepository;
import com.example.asianfoodapp.auth.domain.User;
import com.example.asianfoodapp.auth.services.UserService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class AsianFoodAppApplication {

    @Value("${offer.http.client.config.uri}")
    private String baseUrl;

    private final UserRepository userRepository;
    private final UserService userService;

    public AsianFoodAppApplication(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AsianFoodAppApplication.class, args);
    }

    @PostConstruct
    public void init() {
        String username = "Admin";
        Optional<User> admin = userRepository.findUserByLogin(username);

        if (admin.isEmpty()) {
            User userAdmin = new User();
            userAdmin.setRole(Role.ADMIN);
            userAdmin.setLogin(username);
            userAdmin.setPassword("Admin1234");
            userAdmin.setEmail("admin@admin.com");
            userService.saveUser(userAdmin);
        }
    }

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
