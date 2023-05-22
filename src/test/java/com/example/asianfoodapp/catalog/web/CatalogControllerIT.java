//package com.example.asianfoodapp.catalog.web;
//
//import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
//import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.CreateRecipeCommand;
//import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
//
//import com.example.asianfoodapp.catalog.domain.Ingredient;
//import com.example.asianfoodapp.catalog.domain.Recipe;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.util.List;
//import java.util.Set;
//
//@SpringBootTest
//@AutoConfigureTestDatabase
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//class CatalogControllerIT {
//
//    @Autowired
//    CatalogUseCase catalog;
//
//    @Autowired
//    CatalogController controller;
//
//    @Autowired
//    IngredientJpaRepository ingredientRepository;
//
//
//    @Test
//    void getAllRecipes() {
//        //given
//        omeletteRecipy();
//
//        //when
//        List<Recipe> recipeList = controller.getAll();
//
//        //then
//        assert
//    }
//
//
//    @Test
//    void addRecipe() {
//    }
//
//    @Test
//    void updateRecipe() {
//    }
//
//    private void omeletteRecipy() {
//        Ingredient ingredient1 = new Ingredient("egg", 3, "");
//        Long id = ingredientRepository.save(ingredient1).getId();
//        catalog.addRecipe(new CreateRecipeCommand("omelette", Set.of(id),40, "smash eggs onto pan", false, true, false));
//
//    }
//
//}