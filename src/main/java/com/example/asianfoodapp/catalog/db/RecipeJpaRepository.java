package com.example.asianfoodapp.catalog.db;

import com.example.asianfoodapp.catalog.domain.Recipe;
import jdk.jfr.Experimental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeJpaRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAll();
}
