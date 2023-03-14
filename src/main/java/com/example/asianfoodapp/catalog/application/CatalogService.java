package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.CatalogServiceUseCase;
import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import com.example.asianfoodapp.catalog.domain.Recipe;

public class CatalogService implements CatalogServiceUseCase {

    private RecipeJpaRepository repository;

    public Recipe addRecipe(CreateRecipeCommad commad) {
        Recipe recipe =
    }
}
