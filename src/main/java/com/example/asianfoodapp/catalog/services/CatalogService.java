package com.example.asianfoodapp.catalog.services;

import com.example.asianfoodapp.auth.configuration.CustomUserDetailsService;
import com.example.asianfoodapp.auth.domain.Role;
import com.example.asianfoodapp.auth.domain.User;
import com.example.asianfoodapp.auth.repository.UserRepository;
import com.example.asianfoodapp.catalog.services.port.CatalogUseCase;
import com.example.asianfoodapp.catalog.repository.IngredientRepository;
import com.example.asianfoodapp.catalog.repository.RecipeRepository;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.domain.dto.CreateIngredientCommandDTO;
import com.example.asianfoodapp.catalog.domain.dto.CreateRecipeCommandDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CatalogService implements CatalogUseCase {

    private final RecipeRepository repository;
    private final IngredientRepository ingredientJpaRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public List<Recipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Recipe> findByName(String name) {
        return repository.findAll()
                .stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Transactional
    @Override
    public Optional<Recipe> addRecipe(CreateRecipeCommandDTO command, String username) {
        Optional<User> user = userRepository.findUserByLogin(username);
        if(user.isEmpty()) throw new UsernameNotFoundException("User not found");

        String recipeName = command.name();
        if(checkIfRecipeAlreadyExist(recipeName)) {
            return Optional.empty();
        }

        Recipe recipe = toRecipe(command, user.get());
        return Optional.of(repository.save(recipe));
    }

    private boolean checkIfRecipeAlreadyExist(String name) {
        return findAll().stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    private Recipe toRecipe(CreateRecipeCommandDTO command, User user) {
        Recipe recipe = new Recipe(command.name(), command.readyInMinutes(), command.instructions(),
                command.vegetarian(), command.vegan(), command.glutenFree(), command.image(), user);
        Set<Ingredient> ingredients = collectIngredients(command.ingredients());
        updateRecipe(recipe, ingredients);
        return recipe;
    }

    private Set<Ingredient> collectIngredients(Set<CreateIngredientCommandDTO> ingredients){
        return ingredients
                .stream()
                .map(ingredient -> ingredientJpaRepository.findByNameIgnoreCaseAndAmountAndUnit(ingredient.name(), ingredient.amount(), ingredient.unit())
                        .orElseGet(() -> ingredientJpaRepository.save(new Ingredient(ingredient.name(), ingredient.amount(), ingredient.unit()))))
                .collect(Collectors.toSet());
    }

    private void updateRecipe(Recipe recipe, Set<Ingredient> ingredients){
        recipe.removeIngredients();
        ingredients.forEach(recipe::addIngredient);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public UpdateRecipeResponse updateRecipe(UpdateRecipeCommand command, String username) {
        var user = userDetailsService.loadUserByUsername(username);

        Optional<Recipe> recipeData = repository.findById(command.getId());
        if (recipeData.isPresent()) {
            Recipe recipe = recipeData.get();

            if (recipe.getAuthor().getUsername().equals(username) || user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                Recipe updateRecipe = updateFields(recipe, command);
                repository.save(updateRecipe);
                return UpdateRecipeResponse.SUCCESS;
            } else return new UpdateRecipeResponse(false, List.of("You are not the owner of this recipe"));
        }
        return new UpdateRecipeResponse(false, List.of("Unable to find a recipe with id: " + command.getId()));
    }

    private Recipe updateFields(Recipe recipe, UpdateRecipeCommand command) {

        if(command.getName() != null) {
            recipe.setName(command.getName());
        }
        if(command.getIngredients() != null) {
            var recipeIngredients = new HashSet<>(recipe.getIngredients());
            var commandRecipeIngredients = command.getIngredients();
            recipe.removeIngredients();

            if(!commandRecipeIngredients.isEmpty()) {
                Set<Ingredient> ingredients = command.getIngredients()
                        .stream()
                        .map(e -> ingredientJpaRepository.findByNameIgnoreCaseAndAmountAndUnit(e.name(), e.amount(), e.unit())
                                .orElse(new Ingredient(e.name(), e.amount(), e.unit())))
                        .collect(Collectors.toSet());

                recipe.setIngredients(ingredients);
            }

            var ingredientsIdToDelete = recipeIngredients
                    .stream()
                    .filter(e -> e.getRecipes().isEmpty())
                    .map(e -> e.getId())
                    .collect(Collectors.toSet());
            ingredientJpaRepository.deleteAllById(ingredientsIdToDelete);
        }
        if(command.getReadyInMinutes() != null) {
            recipe.setReadyInMinutes(command.getReadyInMinutes());
        }
        if(command.getInstructions() != null) {
            recipe.setInstructions(command.getInstructions());
        }
        if(command.getVegetarian() != null) {
            recipe.setVegetarian(command.getVegetarian());
        }
        if(command.getVegan() != null) {
            recipe.setVegan(command.getVegan());
        }
        if(command.getGlutenFree() != null) {
            recipe.setGlutenFree(command.getGlutenFree());
        }
        if(command.getImageUrl() != null) {
            recipe.setImageUrl(command.getImageUrl());
        }
        return recipe;
    }
}
