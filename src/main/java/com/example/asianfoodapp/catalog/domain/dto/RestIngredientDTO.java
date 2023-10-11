package com.example.asianfoodapp.catalog.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RestIngredientDTO(
        @NotBlank
        String name,
        @NotNull
        @PositiveOrZero
        Double amount,
        @NotNull String unit){

    public CreateIngredientCommandDTO toCreateCommand() {
        return new CreateIngredientCommandDTO(this.name, this.amount, this.unit);

    }
}
