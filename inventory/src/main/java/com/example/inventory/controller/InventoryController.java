package com.example.inventory.controller;

import com.example.inventory.dto.InventoryResponse;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("api/inventory/check")
    public ResponseEntity<InventoryResponse> checkAvailability(@RequestParam String productId , @RequestParam int quantity){
        log.info("Request recieved to check availability");
        InventoryResponse response = inventoryService.checkAvailability(productId,quantity);
        return ResponseEntity.ok(response);
    }
}
