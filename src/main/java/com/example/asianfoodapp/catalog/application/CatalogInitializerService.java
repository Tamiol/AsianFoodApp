package com.example.asianfoodapp.catalog.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CatalogInitializerService {

    private final RestTemplate restTemplate;
    @Value("${app.api_key}")
    private String API_KEY;

    public CatalogInitializerService(Environment env) {
        restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String home() {
        return restTemplate.getForObject("https://api.spoonacular.com/recipes/complexSearch" +
                        "?cuisine=asian&apiKey=" + API_KEY + "&offset=100&number=100",
                String.class);
    }
}



//to get recipes https://api.spoonacular.com/recipes/complexSearch
//{{baseUrl}}/recipes/complexSearch?cuisine=asian&apiKey=002fc8cff38a4a65a0826b6a1c741d9d&offset=100&number=100

// information about
// {{baseUrl}}/recipes/:id/information?includeNutrition=false&apiKey=002fc8cff38a4a65a0826b6a1c741d9d