package com.example.tracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tracker.model.Ingredient;
import com.example.tracker.model.Recipe;
import com.example.tracker.model.ShoppingList;
import com.example.tracker.service.ShoppingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shopping")
@RequiredArgsConstructor
public class ShoppingController {

	private final ShoppingService shoppingService;

	@GetMapping
    public ResponseEntity<?> getLists() {
        try {
            List<ShoppingList> lists = shoppingService.getAllLists();
            return ResponseEntity.ok(lists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving shopping lists: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getList(@PathVariable Long id) {
        try {
            ShoppingList list = shoppingService.getList(id);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Shopping list not found: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createList(@RequestBody ShoppingList list) {
        try {
            ShoppingList created = shoppingService.createList(list);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating shopping list: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateList(@PathVariable Long id, @RequestBody ShoppingList list) {
        try {
            ShoppingList updated = shoppingService.updateList(id, list);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating shopping list: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteList(@PathVariable Long id) {
        try {
            shoppingService.deleteList(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error deleting shopping list: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<?> addIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        try {
            ShoppingList list = shoppingService.addIngredient(id, ingredient);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding ingredient: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/recipes")
    public ResponseEntity<?> addRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        try {
            ShoppingList list = shoppingService.addRecipe(id, recipe.getId());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding recipe: " + e.getMessage());
        }
    }
}
