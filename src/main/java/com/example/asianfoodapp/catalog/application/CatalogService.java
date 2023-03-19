package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogServiceUseCase;
import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import com.example.asianfoodapp.catalog.domain.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatalogService implements CatalogServiceUseCase {

    private final RecipeJpaRepository repository;

    @Override
    public Recipe addRecipe(CreateRecipeCommand command) {
        Recipe recipe = new Recipe(command.getName(), command.getReadyInMinutes(), command.getInstructions(),
                command.isVegetarian(), command.isVegan(), command.isGlutenFree());
        return repository.save(recipe);
    }
}
