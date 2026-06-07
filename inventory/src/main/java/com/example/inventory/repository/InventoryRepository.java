package com.example.inventory.repository;

public interface InventoryRepository {

    public boolean isAvailable(String productId, int quantity);
}
