package com.example.asianfoodapp.catalog.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CatalogInitializerService {

    private final RestTemplate restTemplate;
    @Value("${app.api-key}")
    private String API_KEY;

    public CatalogInitializerService() {
        restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public String home() {
        return restTemplate.getForObject("https://api.spoonacular.com/recipes/complexSearch" +
                        "?cuisine=asian&apiKey=" + API_KEY + "&offset=100&number=100",
                String.class);
    }
}

// information about
// {{baseUrl}}/recipes/:id/information?includeNutrition=false&apiKey=002fc8cff38a4a65a0826b6a1c741d9d