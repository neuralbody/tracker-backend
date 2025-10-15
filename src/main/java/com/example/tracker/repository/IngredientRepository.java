package com.example.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tracker.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
