package com.example.asianfoodapp.catalog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double amount;
    private String unit;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Recipe> recipes = new HashSet<>();

}
