package com.example.asianfoodapp.catalog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Ingredient {

    private String name;
    private double amount;
    private String unit;
    private ArrayList<Recipe> recipes;

}
