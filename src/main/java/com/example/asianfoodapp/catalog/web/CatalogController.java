package com.example.asianfoodapp.catalog.web;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.CreateRecipeCommand;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.UpdateRecipeCommand;
import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.UpdateRecipeResponse;
import com.example.asianfoodapp.catalog.domain.Recipe;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> getAll() {
        return catalog.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id) {
        return catalog.findById(id)
                .map(recipe -> ResponseEntity.ok(recipe))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateRecipe(@PathVariable Long id, @RequestBody RestRecipeCommand command) {
        UpdateRecipeResponse response = catalog.updateRecipe(command.toUpdateCommand(id));

        if(!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addRecipe(@Valid @RequestBody RestRecipeCommand command) {
        Recipe recipe = catalog.addRecipe(command.toCreateCommand());
        URI uri = createRecipeUri(recipe);
        return ResponseEntity.created(uri).build();
    }

    private URI createRecipeUri(Recipe recipe) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + recipe.getId().toString()).build().toUri();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        catalog.removeById(id);
    }


    @Data
    static class RestRecipeCommand {

        @NotBlank
        private String name;

        @NotEmpty
        private Set<Long> ingredients;

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

        CreateRecipeCommand toCreateCommand() {
            return new CreateRecipeCommand(this.name, this.ingredients, this.readyInMinutes, this.instructions,
                    this.vegetarian, this.vegan, this.glutenFree);
        }

        UpdateRecipeCommand toUpdateCommand(Long id){
            return new UpdateRecipeCommand(id, this.name, this.ingredients, this.readyInMinutes, this.instructions,
                    this.vegetarian, this.vegan, this.glutenFree);
        }
    }
}
