package com.example.asianfoodapp.catalog.domain.mapper;

import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommand;
import com.example.asianfoodapp.catalog.domain.dto.IngredientDTO;
import com.example.asianfoodapp.catalog.domain.dto.RestRecipeDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RestRecipeDTOMapper implements Function<RestRecipeDTO, CreateRecipeCommand> {
    @Override
    public CreateRecipeCommand apply(RestRecipeDTO recipe) {
        return new CreateRecipeCommand(
                recipe.getName(),
                recipe.getIngredients()
                        .stream()
                        .map(IngredientDTO::toCreateCommand)
                        .collect(Collectors.toSet()),
                recipe.getReadyInMinutes(),
                recipe.getInstructions(),
                recipe.getVegetarian(),
                recipe.getVegan(),
                recipe.getGlutenFree());
    }
}
