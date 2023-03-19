package com.example.asianfoodapp.catalog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
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

    private LocalDateTime createdAt;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Ingredient> ingredients = new HashSet<>();
    private int readyInMinutes;
    private String instructions;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;

}

