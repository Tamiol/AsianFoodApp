package com.example.asianfoodapp.catalog.domain.dto;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.UpdateRecipeCommand;
import com.example.asianfoodapp.catalog.domain.mapper.RestRecipeDTOMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Component
public class RestRecipeDTO {

    private final RestRecipeDTOMapper mapper;

    @NotBlank
    private String name;

    @NotEmpty
    private Set<IngredientDTO> ingredients;

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
        Set<CreateIngredientCommand> ingredientsCommand = this.ingredients.stream().map(IngredientDTO::toCreateCommand).collect(Collectors.toSet());
        return new CreateRecipeCommand(this.name, ingredientsCommand, this.readyInMinutes, this.instructions,
                this.vegetarian, this.vegan, this.glutenFree);
    }

    public UpdateRecipeCommand toUpdateCommand(Long id){
        return new UpdateRecipeCommand(id, this.name, this.ingredients, this.readyInMinutes, this.instructions,
                this.vegetarian, this.vegan, this.glutenFree);
    }
}
