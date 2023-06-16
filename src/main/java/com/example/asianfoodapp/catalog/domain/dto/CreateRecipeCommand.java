package com.example.asianfoodapp.catalog.domain.dto;

import lombok.Value;

import java.util.Set;

public record CreateRecipeCommand(String name, Set<CreateIngredientCommand> ingredients, Integer readyInMinutes,
                           String instructions, Boolean vegetarian, Boolean vegan, Boolean glutenFree) {
}
