package com.example.asianfoodapp.catalog.services;

import com.example.asianfoodapp.auth.domain.User;
import com.example.asianfoodapp.auth.repository.UserRepository;
import com.example.asianfoodapp.catalog.services.port.CatalogInitializerUseCase;
import com.example.asianfoodapp.catalog.services.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.repository.IngredientRepository;
import com.example.asianfoodapp.catalog.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;


@RestController
public class CatalogInitializerService implements CatalogInitializerUseCase {

    private final WebClient webClient;
    private final CatalogUseCase catalog;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientJpaRepository;
    @Value("${app.api-key}")
    private String API_KEY;

    private final String USERNAME = "Admin";

    @Autowired
    public CatalogInitializerService(CatalogUseCase catalog, IngredientRepository ingredientJpaRepository, WebClient webclient, UserRepository userRepository) {
        this.catalog = catalog;
        this.ingredientJpaRepository = ingredientJpaRepository;
        this.webClient = webclient;
        this.userRepository = userRepository;
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
                .bodyToMono(ApiFetchDataDTO.class)
                .block();

        Set<Long> ids = new HashSet<>();
        response.results().forEach(result ->
            ids.add(result.id()));

        ids.forEach(this::initRecipe);
    }

    private void initRecipe(Long id) {
        var response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + id)
                        .path("/information")
                        .queryParam("includeNutrition", false)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(ApiFetchRecipeDTO.class)
                .block();

        CreateRecipeCommandDTO command = getRecipeFromApi(response);

        catalog.addRecipe(command, this.USERNAME);
    }

    private CreateRecipeCommandDTO getRecipeFromApi(ApiFetchRecipeDTO response) {
        Set<CreateIngredientCommandDTO> ingredients = new HashSet<>();
        for (ApiFetchIngredientDTO recipe : response.extendedIngredients()) {
            CreateIngredientCommandDTO ingredient = new CreateIngredientCommandDTO(
                    recipe.name(),
                    recipe.amount(),
                    recipe.unit());
            ingredients.add(ingredient);
        }

        return new CreateRecipeCommandDTO(
                response.title(),
                ingredients,
                response.readyInMinutes(),
                response.instructions(),
                response.vegetarian(),
                response.vegan(),
                response.glutenFree(),
                response.image()
        );
    }
}