package com.example.asianfoodapp.catalog.services;

import com.example.asianfoodapp.BaseIT;
import com.example.asianfoodapp.auth.domain.Role;
import com.example.asianfoodapp.auth.domain.User;
import com.example.asianfoodapp.auth.repository.UserRepository;
import com.example.asianfoodapp.catalog.domain.dto.CreateIngredientCommandDTO;
import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommandDTO;
import com.example.asianfoodapp.catalog.domain.dto.RestIngredientDTO;
import com.example.asianfoodapp.catalog.repository.IngredientRepository;
import com.example.asianfoodapp.catalog.repository.RecipeRepository;
import com.example.asianfoodapp.catalog.services.port.CatalogUseCase.UpdateRecipeCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.webjars.NotFoundException;
import java.util.Set;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CatalogServiceTest extends BaseIT {

    @Autowired
    private RecipeRepository repository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private CatalogService service;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUpDatabaseWithUSERAniaOwnsRecipeAndADMINAndUSERSzymon() {
        repository.deleteAll();
        userRepository.deleteAll();
        User owner = new User("Ania", "email@w.pl", "12345678", Role.USER);
        var saverOwner = userRepository.save(owner);
        User user = new User("Szymon", "emailSzymon@w.pl", "12345678", Role.USER);
        userRepository.save(user);

        if(userRepository.findUserByLogin("Admin").isEmpty()) {
            User admin = new User("Admin", "emailAdmin@w.pl", "12345678", Role.ADMIN);
            userRepository.save(admin);
        }
        CreateIngredientCommandDTO ingredient = new CreateIngredientCommandDTO("egg", 3, "");
        CreateRecipeCommandDTO recipe = new CreateRecipeCommandDTO(
                "omelette",
                Set.of(ingredient),
                40,
                "description",
                true,
                false,
                false,
                "www.pic.com"
        );
        service.addRecipe(recipe, saverOwner.getUsername());
    }

    @Test
    void ownerUpdateEveryRecipeField() {
        //given
        var id = repository.findByName("omelette")
                .orElseThrow(() -> new NotFoundException("Recipe not found"))
                .getId();
        var updateName = "shrimps";
        var updateReadyInMinutes = 20;
        var updateInstructions = "description";
        var updateVegetarian = false;
        var updateVegan = true;
        var updateGlutenFree = true;
        var updateImageUrl = "www.zdj.pl";
        var updateIngredient = new RestIngredientDTO("egg", 4d, "unit");

        UpdateRecipeCommand fieldsToUpdate = UpdateRecipeCommand.builder()
                .id(id)
                .name(updateName)
                .readyInMinutes(updateReadyInMinutes)
                .instructions(updateInstructions)
                .vegetarian(updateVegetarian)
                .vegan(updateVegan)
                .glutenFree(updateGlutenFree)
                .imageUrl(updateImageUrl)
                .ingredients(Set.of(updateIngredient))
                .build();

        //when
        service.updateRecipe(fieldsToUpdate, "Ania");

        //then
        var updatedRecipe = service.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        assertEquals(updateName, updatedRecipe.getName());
        assertEquals(updateReadyInMinutes, updatedRecipe.getReadyInMinutes());
        assertEquals(updateInstructions, updatedRecipe.getInstructions());
        assertEquals(updateVegetarian, updatedRecipe.getVegetarian());
        assertEquals(updateVegan, updatedRecipe.getVegan());
        assertEquals(updateGlutenFree, updatedRecipe.getGlutenFree());
        assertEquals(updateImageUrl, updatedRecipe.getImageUrl());
        assertThat(ingredientRepository.findAll()).hasSize(1);
    }

    @Test
    void ownerUpdateOnlyTwoFields() {
        //given
        var id = repository.findByName("omelette")
                .orElseThrow(() -> new NotFoundException("Recipe not found"))
                .getId();
        var updateName = "shrimps";
        var updateVegan = true;
        UpdateRecipeCommand fieldsToUpdate = UpdateRecipeCommand.builder()
                .id(id)
                .name(updateName)
                .vegan(updateVegan)
                .build();

        //when
        service.updateRecipe(fieldsToUpdate, "Ania");

        //then
        var updatedRecipe = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        assertEquals(updateName, updatedRecipe.getName());
        assertEquals(updateVegan, updatedRecipe.getVegan());
        assertEquals(40, updatedRecipe.getReadyInMinutes());
    }

    @Test
    void notOwnerTryToUpdateFieldAndGetsError() {
        //given
        var id = repository.findByName("omelette")
                .orElseThrow(() -> new NotFoundException("Recipe not found")).getId();
        var updateName = "shrimps";
        UpdateRecipeCommand fieldsToUpdate = UpdateRecipeCommand.builder()
                .id(id)
                .name(updateName)
                .build();

        //when
        var response = service.updateRecipe(fieldsToUpdate, "Szymon");

        //then
        assertFalse(response.success());
        var updatedRecipe = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        assertEquals("omelette", updatedRecipe.getName());
    }

    @Test
    void adminUpdateRecipeField() {
        //given
        var id = repository.findByName("omelette")
                .orElseThrow(() -> new NotFoundException("Recipe not found"))
                .getId();
        var updateName = "shrimps";
        UpdateRecipeCommand fieldsToUpdate = UpdateRecipeCommand.builder()
                .id(id)
                .name(updateName)
                .build();

        //when
        var response = service.updateRecipe(fieldsToUpdate, "Szymon");

        //then
        assertFalse(response.success());
        var updatedRecipe = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));
        assertEquals("omelette", updatedRecipe.getName());
    }
}