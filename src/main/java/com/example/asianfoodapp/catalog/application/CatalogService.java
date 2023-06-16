package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.domain.dto.CreateIngredientCommand;
import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CatalogService implements CatalogUseCase {

    private final RecipeJpaRepository repository;
    private final IngredientJpaRepository ingredientJpaRepository;

    @Override
    public List<Recipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Recipe> findOneByName(String name) {
        return Optional.empty();
    }


    @Override
    public Recipe addRecipe(CreateRecipeCommand command) {
        Recipe recipe = toRecipe(command);
        return repository.save(recipe);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UpdateRecipeResponse updateRecipe(UpdateRecipeCommand command) {
        return repository.findById(command.getId())
                .map(recipe -> {
                    Recipe updateRecipe = updateFields(recipe, command);
                    repository.save(updateRecipe);
                    return UpdateRecipeResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateRecipeResponse(false, List.of("Unable to find a recipe with id: " + command.getId())));
    }

    private Recipe updateFields(Recipe recipe, UpdateRecipeCommand command) {

        if(command.getName() != null) {
            recipe.setName(command.getName());
        }
//        if(!command.getIngredients().isEmpty()) {
//            recipe.setReadyInMinutes(command.getReadyInMinutes());
//        }
        if(command.getReadyInMinutes() != null) {
            recipe.setReadyInMinutes(command.getReadyInMinutes());
        }
        if(command.getInstructions() != null) {
            recipe.setInstructions(command.getInstructions());
        }
        if(command.getVegetarian() != null) {
            recipe.setVegetarian(command.getVegetarian());
        }
        if(command.getVegan() != null) {
            recipe.setVegan(command.getVegan());
        }
        if(command.getGlutenFree() != null) {
            recipe.setGlutenFree(command.getGlutenFree());
        }

        return recipe;
    }

    private Recipe toRecipe(CreateRecipeCommand command) {
        //TODO mapper
        Recipe recipe = new Recipe(command.name(), command.readyInMinutes(), command.instructions(),
                command.vegetarian(), command.vegan(), command.glutenFree());
        Set<Ingredient> ingredients = collectIngredients(command.ingredients());
        updateRecipe(recipe, ingredients);
        return recipe;
    }

    private Set<Ingredient> collectIngredients(Set<CreateIngredientCommand> ingredients){
        return ingredients
                .stream()
                .map(ingredient -> ingredientJpaRepository.findByNameIgnoreCaseAndAmountAndUnit(ingredient.name(), ingredient.amount(), ingredient.unit())
                        .orElseGet(() -> ingredientJpaRepository.save(new Ingredient(ingredient.name(), ingredient.amount(), ingredient.unit()))))
                .collect(Collectors.toSet());
    }

    private void updateRecipe(Recipe recipe, Set<Ingredient> ingredients){
        recipe.removeIngredients();
        ingredients.forEach(recipe::addIngredient);
    }
}
