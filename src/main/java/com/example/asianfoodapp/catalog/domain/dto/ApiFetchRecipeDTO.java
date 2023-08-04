package com.example.asianfoodapp.catalog.domain.dto;

import java.util.ArrayList;

public record ApiFetchRecipeDTO (boolean vegetarian,
                                 boolean vegan,
                                 boolean glutenFree,
                                 ArrayList<ApiFetchIngredientDTO> extendedIngredients,
                                 String title,
                                 int readyInMinutes,
                                 String instructions){

}
