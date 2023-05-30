package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.application.port.IngredientUseCase;
import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IngredientService implements IngredientUseCase {

    private final IngredientJpaRepository repository;

    @Override
    public Optional<Ingredient> findByNameAndAmountAndUnit (String name, double amount, String unit) {
        return repository.findByNameIgnoreCaseAndAmountAndUnit(name, amount, unit);
    }
}
