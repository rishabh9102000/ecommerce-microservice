package com.example.inventory.service;

import com.example.inventory.dto.InventoryResponse;
import com.example.inventory.exception.OutOfStockException;

public interface InventoryService {

    public InventoryResponse checkAvailability(String productId, int qunatity);
}
