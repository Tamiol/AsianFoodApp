package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogInitializerUseCase;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.dto.CreateIngredientCommandDTO;
import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommandDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;


@RestController
public class CatalogInitializerService implements CatalogInitializerUseCase {

    private final WebClient webClient;
    private final CatalogUseCase catalog;
    private final IngredientJpaRepository ingredientJpaRepository;
    @Value("${app.api-key}")
    private String API_KEY;

    @Autowired
    public CatalogInitializerService(CatalogUseCase catalog, IngredientJpaRepository ingredientJpaRepository, WebClient webclient) {
        this.catalog = catalog;
        this.ingredientJpaRepository = ingredientJpaRepository;
        this.webClient = webclient;
    }

    @Override
    public void fetchData(int offset, int number) {
        var response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/complexSearch")
                        .queryParam("cuisine", "asian")
                        .queryParam("apiKey", API_KEY)
                        .queryParam("offset", offset)
                        .queryParam("number", number)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode resultsNode = jsonNode.get("results");

        Set<Long> ids = new HashSet<>();
        resultsNode.forEach(result ->
            ids.add(result.get("id").asLong())
        );

        ids.forEach(this::initRecipe);
    }

    private void initRecipe(Long id) {
        var response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/" + id)
                        .path("/information")
                        .queryParam("includeNutrition", false)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode ingredientsNodes;
        try {
            ingredientsNodes = mapper.readTree(response).get("extendedIngredients");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Set<CreateIngredientCommandDTO> ingredients = new HashSet<>();
        for (JsonNode ingNode : ingredientsNodes) {
            CreateIngredientCommandDTO ingredient = new CreateIngredientCommandDTO(
                    ingNode.get("name").asText(),
                    ingNode.get("measures").get("metric").get("amount").asDouble(),
                    ingNode.get("measures").get("metric").get("unitLong").asText());
            ingredients.add(ingredient);
        }

        CreateRecipeCommandDTO command = new CreateRecipeCommandDTO(
                node.get("title").asText(),
                ingredients,
                node.get("readyInMinutes").asInt(),
                node.get("instructions").asText(),
                node.get("vegetarian").asBoolean(),
                node.get("vegan").asBoolean(),
                node.get("glutenFree").asBoolean()
        );

        catalog.addRecipe(command);
    }

    private Ingredient getOrCreateIngredient(CreateIngredientCommandDTO ingredient ) {
        return ingredientJpaRepository
                .findByNameIgnoreCaseAndAmountAndUnit(ingredient.name(), ingredient.amount(), ingredient.unit())
                .orElseGet(() -> ingredientJpaRepository.save(
                        new Ingredient(ingredient.name(),
                                ingredient.amount(),
                                ingredient.unit())));
    }
}