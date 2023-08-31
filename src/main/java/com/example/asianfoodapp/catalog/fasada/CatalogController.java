package com.example.asianfoodapp.catalog.fasada;

import com.example.asianfoodapp.catalog.services.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.services.port.CatalogUseCase.UpdateRecipeResponse;
import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.domain.dto.RestRecipeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.core.Authentication;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> getAll(@RequestParam Optional<String> name) {

        if(name.isPresent()){
            return catalog.findByName(name.get());
        }
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
    public void updateRecipe(@PathVariable Long id, @RequestBody RestRecipeDTO command) {
        UpdateRecipeResponse response = catalog.updateRecipe(command.toUpdateCommand(id));

        if(!response.success()) {
            String message = String.join(", ", response.errors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addRecipe(@Valid @RequestBody RestRecipeDTO command, Authentication authentication) {
        Optional<Recipe> response = catalog.addRecipe(command.toCreateCommand(), authentication.getName());

        if(response.isPresent()){
            URI uri = createRecipeUri(response.get());
            return ResponseEntity.created(uri).build();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe with provided name: " + command.name() + "already exist");
    }

    private URI createRecipeUri(Recipe recipe) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + recipe.getId().toString()).build().toUri();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        catalog.removeById(id);
    }
}
