package com.example.asianfoodapp.catalog.domain.dto;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.CreateRecipeCommand;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.UpdateRecipeCommand;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Set;

@Data
public class RestRecipeCommandDTO {

    @NotBlank
    private String name;

    @NotEmpty
    private Set<IngredientCommandDTO> ingredients;

    @NotNull
    @Positive
    private Integer readyInMinutes;

    @NotBlank
    private String instructions;

    @NotNull
    private Boolean vegetarian;

    @NotNull
    private Boolean vegan;

    @NotNull
    private Boolean glutenFree;

    public CreateRecipeCommand toCreateCommand() {
        return new CreateRecipeCommand(this.name, this.ingredients, this.readyInMinutes, this.instructions,
                this.vegetarian, this.vegan, this.glutenFree);
    }

    public UpdateRecipeCommand toUpdateCommand(Long id){
        return new UpdateRecipeCommand(id, this.name, this.ingredients, this.readyInMinutes, this.instructions,
                this.vegetarian, this.vegan, this.glutenFree);
    }
}
