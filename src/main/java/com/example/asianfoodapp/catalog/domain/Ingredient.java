package com.example.asianfoodapp.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString(exclude = "recipes")
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double amount;
    private String unit;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "ingredients")
    @JsonIgnoreProperties("ingredients")
    private Set<Recipe> recipes = new HashSet<>();

    public Ingredient(String name, double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}
