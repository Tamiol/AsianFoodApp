package com.example.asianfoodapp.catalog.repository;

import com.example.asianfoodapp.BaseIT;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class RecipeJpaRepositoryTest extends BaseIT {

    @Autowired
    private RecipeRepository repository;

    @Test
    void shouldFindAll() {
        //given
        Ingredient ingredient = new Ingredient("egg", 3, "");
        Recipe recipe = new Recipe(
                "omelette",
                Set.of(ingredient),
                40,
                "description",
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