package com.example.asianfoodapp.catalog.repository;

import com.example.asianfoodapp.catalog.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByNameIgnoreCaseAndAmountAndUnit(String name, double amount, String unit);
}
