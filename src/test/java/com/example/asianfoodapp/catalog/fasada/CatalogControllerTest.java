package com.example.asianfoodapp.catalog.fasada;

import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.repository.IngredientRepository;
import com.example.asianfoodapp.catalog.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecipeRepository recipeRepository;
    @MockBean
    IngredientRepository ingredientRepository;

    ObjectMapper objectMapper;

    @Test
    void shouldFindAllRecipes() throws Exception {
        //given
        Ingredient ingredient = new Ingredient("egg", 3, "");
        var recipes = List.of(new Recipe("omelette", Set.of(ingredient),40, "description", true, false, false),
                new Recipe("shrimps", 120, "description", false, false, true));

        // when
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        //then
        mockMvc.perform(get("/catalog"))
                .andExpectAll(status().isOk(),
                        content().json(objectMapper.writeValueAsString(recipes)));
    }
}