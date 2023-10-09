package com.example.asianfoodapp.catalog.services.port;

import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommandDTO;
import com.example.asianfoodapp.catalog.domain.dto.RestIngredientDTO;
import lombok.*;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CatalogUseCase {

    List<Recipe> findAll();

    Optional<Recipe> findById(Long id);
    List<Recipe> findByName(String name);

    Optional<Recipe> addRecipe(CreateRecipeCommandDTO command, String username);

    void removeById(Long id);

    UpdateRecipeResponse updateRecipe(UpdateRecipeCommand command, String username);

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    class UpdateRecipeCommand {
        Long id;
        String name;
        Set<RestIngredientDTO> ingredients;
        Integer readyInMinutes;
        String instructions;
        Boolean vegetarian;
        Boolean vegan;
        Boolean glutenFree;
        String imageUrl;
    }

    record UpdateRecipeResponse(boolean success, List<String> errors) {
        public static final UpdateRecipeResponse SUCCESS = new UpdateRecipeResponse(true, Collections.emptyList());
    }
}
