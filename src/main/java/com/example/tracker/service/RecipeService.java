package com.example.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tracker.model.Recipe;
import com.example.tracker.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecipeService {
	
	private final RecipeRepository recipeRepo;

	public List<Recipe> getAllRecipes() {
		this.log.info("Fetching all recipes");
        return recipeRepo.findAll();
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepo.save(recipe);
    }
}
