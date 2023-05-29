package com.example.asianfoodapp.catalog.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientCommandDTO {

    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private Double amount;

    @NotNull
    private String unit;
}
