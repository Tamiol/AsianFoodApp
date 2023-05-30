package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.domain.Ingredient;

import java.util.Optional;

public interface IngredientUseCase {

    Optional<Ingredient> findByNameAndAmountAndUnit (String name, double amount, String unit);
}
