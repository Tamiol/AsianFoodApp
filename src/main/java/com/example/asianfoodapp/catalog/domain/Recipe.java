package com.example.asianfoodapp.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Recipe {

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

