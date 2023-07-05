package com.example.asianfoodapp.catalog.domain.dto;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.UpdateRecipeCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;
import java.util.stream.Collectors;

public record RestRecipeDTO(
            @NotBlank
            String name,
            @NotEmpty
            Set<RestIngredientDTO> ingredients,
            @NotNull
            @Positive
            Integer readyInMinutes,
            @NotBlank
            String instructions,
            @NotNull
            Boolean vegetarian,
            @NotNull
            Boolean vegan,
            @NotNull
            Boolean glutenFree){

    //TODO zamienić to na mapper
    public CreateRecipeCommandDTO toCreateCommand() {
        Set<CreateIngredientCommandDTO> ingredientsCommand = this.ingredients.stream().map(RestIngredientDTO::toCreateCommand).collect(Collectors.toSet());
        return new CreateRecipeCommandDTO(this.name, ingredientsCommand, this.readyInMinutes, this.instructions,
                this.vegetarian, this.vegan, this.glutenFree);
    }

    public UpdateRecipeCommand toUpdateCommand(Long id){
        return new UpdateRecipeCommand(id, this.name, this.ingredients, this.readyInMinutes, this.instructions,
                this.vegetarian, this.vegan, this.glutenFree);
    }
}
