package com.example.tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tracker.model.Ingredient;
import com.example.tracker.model.Recipe;
import com.example.tracker.model.ShoppingList;
import com.example.tracker.repository.IngredientRepository;
import com.example.tracker.repository.RecipeRepository;
import com.example.tracker.repository.ShoppingListRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ShoppingService {

	private final ShoppingListRepository listRepo;
    private final RecipeRepository recipeRepo;
    private final IngredientRepository ingredientRepo;

    @Transactional(readOnly = true)
    public List<ShoppingList> getAllLists() {
    	this.log.info("Fetching all shopping lists");
        return listRepo.findAll();
    }

    @Transactional(readOnly = true)
    public ShoppingList getList(Long id) {
        return listRepo.findById(id).orElseThrow();
    }

    @Transactional
    public ShoppingList createList(ShoppingList list) {
        return listRepo.save(list);
    }

    @Transactional
    public ShoppingList updateList(Long id, ShoppingList updated) {
        ShoppingList existing = getList(id);
        existing.setTitle(updated.getTitle());
        existing.setIngredients(updated.getIngredients());
        existing.setRecipes(updated.getRecipes());
        return listRepo.save(existing);
    }

    @Transactional
    public void deleteList(Long id) {
        listRepo.deleteById(id);
    }

    @Transactional
    public ShoppingList addIngredient(Long listId, Ingredient ingredient) {
        ShoppingList list = getList(listId);
        list.getIngredients().add(ingredientRepo.save(ingredient));
        return listRepo.save(list);
    }

    @Transactional
    public ShoppingList addRecipe(Long listId, Long recipeId) {
        ShoppingList list = getList(listId);
        Recipe recipe = recipeRepo.findById(recipeId).orElseThrow();
        list.getRecipes().add(recipe);
        return listRepo.save(list);
    }
}
