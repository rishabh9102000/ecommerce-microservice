package com.example.inventory.repository;


import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InventoryRepositoryImpl implements  InventoryRepository{

    Map<String,Integer> inventory = Map.of("P101", 10, "P102", 0);


    @Override
    public boolean isAvailable(String productId, int quantity) {
        return inventory.containsKey(productId) && inventory.get(productId) >= quantity;
    }
}
