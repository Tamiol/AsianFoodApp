package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import com.example.asianfoodapp.catalog.domain.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CatalogService implements CatalogUseCase {

    private final RecipeJpaRepository repository;

    @Override
    public List<Recipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Recipe addRecipe(CreateRecipeCommand command) {
        Recipe recipe = new Recipe(command.name(), command.readyInMinutes(), command.instructions(),
                command.vegetarian(), command.vegan(), command.glutenFree());
        return repository.save(recipe);
    }
}
