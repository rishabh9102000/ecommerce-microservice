package com.example.inventory.service;

import com.example.inventory.dto.InventoryResponse;
import com.example.inventory.exception.OutOfStockException;
import com.example.inventory.repository.InventoryRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository){
        this.inventoryRepository= inventoryRepository;
    }

    @Override
    public InventoryResponse checkAvailability(String productId, int qunatity)  {
        log.info("Entered the checkAvailability method");
        if(inventoryRepository.isAvailable(productId,qunatity)){
            InventoryResponse response = InventoryResponse.builder()
                    .isAvailable(true)
                    .message("Product " + productId + " is available")
                    .build();
            log.info("Product {} is available", productId);
            return response;
        }
        else{
            log.warn("Product not found: {}", productId);
            throw new OutOfStockException("Product not found: " + productId);
        }

    }
}
