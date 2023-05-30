//package com.example.asianfoodapp.catalog.web;
//
//import com.example.asianfoodapp.catalog.application.port.CatalogUseCase;
//import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.CreateIngredientCommand;
//import com.example.asianfoodapp.catalog.application.port.CatalogUseCase.CreateRecipeCommand;
//import com.example.asianfoodapp.catalog.db.IngredientJpaRepository;
//
//import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
//import com.example.asianfoodapp.catalog.domain.Recipe;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.DirtiesContext.ClassMode;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@AutoConfigureTestDatabase
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//class CatalogControllerTest {
//
//    @MockBean
//    private RecipeJpaRepository recipeRepository;
//
//    @Autowired
//    CatalogUseCase catalog;
//
//    @Autowired
//    CatalogController controller;
//
//    @Test
//    void addRecipe() {
//        //given
//        CreateIngredientCommand ingredient1 = new CreateIngredientCommand("egg", 3.0, "");
//        CreateRecipeCommand recipe = new CreateRecipeCommand("omelette", Set.of(ingredient1),40, "Pour eggs into the pan and spread evenly. Cook until eggs are set and fillings are heated. Slide onto a plate and serve hot.", true, false, false);
//
//        // when
//        catalog.addRecipe(recipe);
//        List<Recipe> recipeList = controller.getAll();
//
//        //then
//        assertEquals(1, recipeList.size());
//    }
//
//    @Test
//    void updateRecipe() {
//    }
//
////    private void omeletteRecipy() {
////        Ingredient ingredient1 = new Ingredient("egg", 3, "");
////        Long id = ingredientRepository.save(ingredient1).getId();
////        catalog.addRecipe(new CreateRecipeCommand("omelette", Set.of(id),40, "smash eggs onto pan", false, true, false));
////
////    }
//
//}