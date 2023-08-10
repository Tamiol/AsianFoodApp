package com.example.asianfoodapp.catalog.repository;

import com.example.asianfoodapp.catalog.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAll();
}
