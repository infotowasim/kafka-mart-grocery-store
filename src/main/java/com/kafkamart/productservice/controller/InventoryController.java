package com.kafkamart.productservice.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kafkamart.productservice.dto.request.InventoryRequest;
import com.kafkamart.productservice.dto.response.InventoryResponse;
import com.kafkamart.productservice.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(
            InventoryService inventoryService) {

        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(
            @RequestBody InventoryRequest request) {

        return ResponseEntity.ok(
                inventoryService.createInventory(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                inventoryService.getInventoryById(id));
    }
    @GetMapping
    public ResponseEntity<List<InventoryResponse>>
    getAllInventories() {

        return ResponseEntity.ok(
                inventoryService.getAllInventories());
    }
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryRequest request) {

        return ResponseEntity.ok(
                inventoryService.updateInventory(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(
            @PathVariable Long id) {

        inventoryService.deleteInventory(id);

        return ResponseEntity.ok(
                "Inventory Deleted Successfully");
    }
}