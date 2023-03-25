package com.example.asianfoodapp.catalog.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public interface CatalogInitializerUseCase {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    class IngredientCommand {
        String name;
        double amount;
        String unit;
    }
}
