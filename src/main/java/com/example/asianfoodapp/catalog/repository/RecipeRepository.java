package com.example.asianfoodapp.catalog.repository;

import com.example.asianfoodapp.catalog.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAll();
    Optional<Recipe> findByName(String name);
    Optional<Recipe> findById(Integer id);
}
