package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.domain.Recipe;
import lombok.Value;

import java.util.List;
import java.util.Set;

public interface CatalogUseCase {

    List<Recipe> findAll();
    Recipe addRecipe(CreateRecipeCommand command);

    @Value
    class CreateRecipeCommand {
    String name;
    Set<Long> ingredients;
    int readyInMinutes;
    String instructions;
    boolean vegetarian;
    boolean vegan;
    boolean glutenFree;

    }
}
