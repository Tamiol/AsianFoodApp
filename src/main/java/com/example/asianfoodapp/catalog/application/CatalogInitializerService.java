package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogInitializerUseCase;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
    private final IngredientJpaRepository ingredientJpaRepository;
    @Value("${app.api-key}")
    private String API_KEY;

    public CatalogInitializerService(CatalogUseCase catalog, IngredientJpaRepository ingredientJpaRepository) {
        this.catalog = catalog;
        this.ingredientJpaRepository = ingredientJpaRepository;
        restTemplate = new RestTemplate();
    }

    @GetMapping("/")
    public void initData() {
        ResponseEntity<String> response = restTemplate.getForEntity("https://api.spoonacular.com/recipes/complexSearch" +
                        "?cuisine=asian&apiKey=" + API_KEY + "&offset=0&number=4",
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
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.spoonacular.com/recipes/{id}/information?includeNutrition=false&apiKey=" + API_KEY,
                String.class,
                id);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // add ingredients
        JsonNode ingredientsNodes;
        try {
            ingredientsNodes = mapper.readTree(response.getBody()).get("extendedIngredients");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Set<Long> ingredients = new HashSet<>();
        for (JsonNode ingNode : ingredientsNodes) {
            IngredientCommand ingredient = IngredientCommand.builder()
                    .name(ingNode.get("name").asText())
                    .amount(ingNode.get("measures").get("metric").get("amount").asDouble())
                    .unit(ingNode.get("measures").get("metric").get("unitLong").asText()).build();


            ingredients.add(getOrCreateIngredient(ingredient).getId());
        }

        //200 code add book
        CreateRecipeCommand command = new CreateRecipeCommand(
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

    private Ingredient getOrCreateIngredient(IngredientCommand ingredient ) {
        return ingredientJpaRepository
                .findByNameIgnoreCaseAndAmountAndUnit(ingredient.getName(), ingredient.getAmount(), ingredient.getUnit())
                .orElseGet(() -> ingredientJpaRepository.save(
                        new Ingredient(ingredient.getName(),
                                ingredient.getAmount(),
                                ingredient.getUnit())));
    }
}