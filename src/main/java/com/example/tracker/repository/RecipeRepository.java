package com.example.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tracker.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
