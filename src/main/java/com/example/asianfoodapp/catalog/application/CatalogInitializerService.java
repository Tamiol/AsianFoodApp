package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogInitializerUseCase;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.example.asianfoodapp.catalog.application.port.CatalogUseCase.*;

@RestController
public class CatalogInitializerService implements CatalogInitializerUseCase {

    private final RestTemplate restTemplate;
    private final CatalogUseCase catalog;
    @Value("${app.api-key}")
    private String API_KEY;

    public CatalogInitializerService(CatalogUseCase catalog) {
        this.catalog = catalog;
        restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public void home() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://api.spoonacular.com/recipes/complexSearch" +
                        "?cuisine=asian&apiKey=" + API_KEY + "&offset=0&number=2",
                String.class);

        // code response 200
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode resultsNode = jsonNode.get("results");

        Set<Long> ids = new HashSet<>();
        resultsNode.forEach(result ->
            ids.add(result.get("id").asLong())
        );

        // collect objects by ids
        ids.forEach(this::initRecipe);
    }

    private void initRecipe(Long id) {
        ResponseEntity<RecipeJson> response = restTemplate.getForEntity(
                "https://api.spoonacular.com/recipes/{id}/information?includeNutrition=false&apiKey=" + API_KEY,
                RecipeJson.class,
                id);

        RecipeJson recipeJson = response.getBody();

        assert recipeJson != null;

        //200 code
        CreateRecipeCommand command = new CreateRecipeCommand(
                recipeJson.getTitle(),
                recipeJson.getReadyInMinutes(),
                recipeJson.getInstructions(),
                recipeJson.isVegetarian(),
                recipeJson.isVegan(),
                recipeJson.isGlutenFree()
        );

        catalog.addRecipe(command);
    }
}

// information about
// {{baseUrl}}/recipes/:id/information?includeNutrition=false&apiKey=002fc8cff38a4a65a0826b6a1c741d9d