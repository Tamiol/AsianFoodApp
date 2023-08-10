package com.example.asianfoodapp.catalog.domain.dto;

import java.util.Set;

public record CreateRecipeCommandDTO(String name, Set<CreateIngredientCommandDTO> ingredients, Integer readyInMinutes,
                                     String instructions, Boolean vegetarian, Boolean vegan, Boolean glutenFree,String image) {
}
