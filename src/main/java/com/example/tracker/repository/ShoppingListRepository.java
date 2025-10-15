package com.example.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tracker.model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

}
