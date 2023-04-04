package com.example.asianfoodapp.catalog.web;

import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CatalogControllerIT {

    @Autowired
    CatalogController controller;

    @Autowired
    RecipeJpaRepository repository;

    @Test
    void getAll() {
        //given
        omeletteRecipy();

        //when
    }


    @Test
    void addRecipe() {
    }

    @Test
    void updateRecipe() {
    }

    private void omeletteRecipy() {
        //Ingredient
        //repository.add(new Recipe("omelette", ,40, "smash eggs onto pan", false, true, false))

    }

}