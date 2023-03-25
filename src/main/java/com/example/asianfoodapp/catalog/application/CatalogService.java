package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    public Recipe addRecipe(CreateRecipeCommand command) {
        Recipe recipe = toRecipe(command);
        return repository.save(recipe);
    }

    private Recipe toRecipe(CreateRecipeCommand command) {
        Recipe recipe = new Recipe(command.getName(), command.getReadyInMinutes(), command.getInstructions(),
                command.isVegetarian(), command.isVegan(), command.isGlutenFree());
        Set<Ingredient> ingredients = fetchIngredientsByIds(command.getIngredients());
        updateRecipe(recipe, ingredients);
        return recipe;
    }

    private void updateRecipe(Recipe recipe, Set<Ingredient> ingredients){
        recipe.removeIngredients();
        ingredients.forEach(recipe::addIngredient);
    }

    private Set<Ingredient> fetchIngredientsByIds(Set<Long> ingredients){
        return ingredients
                .stream()
                .map(ingredientId -> ingredientJpaRepository.findById(ingredientId)
                        .orElseThrow(() -> new IllegalArgumentException("Cannot find ingredient with id: " + ingredientId)))
                .collect(Collectors.toSet());


    }
}
