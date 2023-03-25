package com.example.asianfoodapp.catalog.db;

import com.example.asianfoodapp.catalog.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientJpaRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByNameIgnoreCaseAndAmountAndUnit(String name, double amount, String unit);
}
