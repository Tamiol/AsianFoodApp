package com.example.asianfoodapp.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "ingredients")
public class Recipe {

    public Recipe(String name, int readyInMinutes, String instructions, boolean vegetarian, boolean vegan, boolean glutenFree) {
        this.name = name;
        this.readyInMinutes = readyInMinutes;
        this.instructions = instructions;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createdAt;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties("recipes")
    private Set<Ingredient> ingredients = new HashSet<>();
    private int readyInMinutes;
    @Column(length = 5000)
    private String instructions;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;

    public void addIngredient(Ingredient ingredient) {
        Recipe self = this;
        ingredients.add(ingredient);
        ingredient.getRecipes().add(self);
    }

    public void removeIngredients() {
        Recipe self = this;
        ingredients.forEach(ingredient -> ingredient.getRecipes().remove(self));
        ingredients.clear();
    }

}

