package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.domain.Recipe;
import lombok.Value;

import java.util.List;

public interface CatalogUseCase {

    List<Recipe> findAll();
    Recipe addRecipe(CreateRecipeCommand command);


    /**
     * @param readyInMinutes Set<Ingredient> ingredients = new HashSet<>();
     */
    record CreateRecipeCommand(String name, int readyInMinutes, String instructions, boolean vegetarian, boolean vegan,
                                   boolean glutenFree) {
    }
}
