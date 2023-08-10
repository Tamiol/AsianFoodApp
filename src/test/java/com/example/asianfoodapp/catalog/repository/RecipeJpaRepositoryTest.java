package com.example.asianfoodapp.catalog.repository;

import com.example.asianfoodapp.BaseIT;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecipeJpaRepositoryTest extends BaseIT {

    @Autowired
    private RecipeRepository repository;

    @Test
    void itShouldFindAll() {
        //given
        Ingredient ingredient = new Ingredient("ingredient1", 3.4, "unit");
        Recipe recipe = new Recipe(
                "recipe1",
                Set.of(ingredient),
                40,
                "instruction",
                true,
                false,
                false
        );
        repository.save(recipe);

        //when
        List<Recipe> list = repository.findAll();

        //then
        assertThat(list.isEmpty()).isFalse();
    }
}