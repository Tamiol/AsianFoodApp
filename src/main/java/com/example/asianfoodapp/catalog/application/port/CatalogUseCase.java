package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommand;
import com.example.asianfoodapp.catalog.domain.dto.IngredientDTO;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CatalogUseCase {

    List<Recipe> findAll();

    Optional<Recipe> findById(Long id);
    Optional<Recipe> findOneByName(String name);

    Recipe addRecipe(CreateRecipeCommand command);

    void removeById(Long id);

    UpdateRecipeResponse updateRecipe(UpdateRecipeCommand command);

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    class UpdateRecipeCommand {
        Long id;
        String name;
        Set<IngredientDTO> ingredients;
        Integer readyInMinutes;
        String instructions;
        Boolean vegetarian;
        Boolean vegan;
        Boolean glutenFree;
    }



    @Value
    class UpdateRecipeResponse {
        public static UpdateRecipeResponse SUCCESS = new UpdateRecipeResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;
    }
}
