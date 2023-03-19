package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public interface CatalogServiceUseCase {

    Recipe addRecipe(CreateRecipeCommand command);
    @Value
    class CreateRecipeCommand {
        String name;
        //Set<Ingredient> ingredients = new HashSet<>();
        int readyInMinutes;
        String instructions;
        boolean vegetarian;
        boolean vegan;
        boolean glutenFree;
    }
}
