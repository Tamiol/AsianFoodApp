package com.example.asianfoodapp.catalog.domain;

import com.example.asianfoodapp.auth.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "ingredients")
@Table(name = "recipe")
@EntityListeners(AuditingEntityListener.class)
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @JsonIgnoreProperties("recipes")
    private Set<Ingredient> ingredients = new HashSet<>();
    private Integer readyInMinutes;
    @Column(length = 5000)
    private String instructions;
    private Boolean vegetarian;
    private Boolean vegan;
    private Boolean glutenFree;
    private String imageUrl;
    @ManyToOne
    @JsonIgnoreProperties("recipes")
    private User author;



    public Recipe(String name, Integer readyInMinutes, String instructions, Boolean vegetarian, Boolean vegan, Boolean glutenFree) {
        this.name = name;
        this.readyInMinutes = readyInMinutes;
        this.instructions = instructions;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
    }

    public Recipe(String name, Set<Ingredient> ingredients, Integer readyInMinutes, String instructions, Boolean vegetarian, Boolean vegan, Boolean glutenFree) {
        this.name = name;
        this.ingredients = ingredients;
        this.readyInMinutes = readyInMinutes;
        this.instructions = instructions;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
    }

    public Recipe(String name, Integer readyInMinutes, String instructions, Boolean vegetarian, Boolean vegan, Boolean glutenFree, String imageUrl, User author) {
        this.name = name;
        this.readyInMinutes = readyInMinutes;
        this.instructions = instructions;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
        this.imageUrl = imageUrl;
        this.author = author;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id) && Objects.equals(name, recipe.name) && Objects.equals(createdAt, recipe.createdAt) && Objects.equals(readyInMinutes, recipe.readyInMinutes) && Objects.equals(instructions, recipe.instructions) && Objects.equals(vegetarian, recipe.vegetarian) && Objects.equals(vegan, recipe.vegan) && Objects.equals(glutenFree, recipe.glutenFree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, readyInMinutes, instructions, vegetarian, vegan, glutenFree);
    }
}

