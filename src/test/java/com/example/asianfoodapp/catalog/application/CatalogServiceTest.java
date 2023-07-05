package com.example.asianfoodapp.catalog.application;

import com.example.asianfoodapp.catalog.db.RecipeJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {

    @Mock
    private RecipeJpaRepository repository;
    private CatalogService service;

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findOneByName() {
    }

    @Test
    void addRecipe() {
    }

    @Test
    void updateRecipe() {
    }
}