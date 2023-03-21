package com.example.asianfoodapp;

import com.example.asianfoodapp.catalog.application.CatalogInitializerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class AsianFoodAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsianFoodAppApplication.class, args);
    }

}
