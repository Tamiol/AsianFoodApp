package com.example.asianfoodapp;

import com.example.asianfoodapp.catalog.application.CatalogInitializerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class AsianFoodAppApplication {

    private String baseUrl = "https://api.spoonacular.com/recipes/";

    public static void main(String[] args) {
        SpringApplication.run(AsianFoodAppApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

}
