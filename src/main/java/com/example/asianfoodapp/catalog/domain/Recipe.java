package com.example.asianfoodapp.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Recipe {

    private Long id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private int readyInMinutes;
    private String instructions;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;

}

