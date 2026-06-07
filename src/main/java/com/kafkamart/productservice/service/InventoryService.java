package com.kafkamart.productservice.service;

import java.util.List;

import com.kafkamart.productservice.dto.request.InventoryRequest;
import com.kafkamart.productservice.dto.response.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);
    InventoryResponse getInventoryById(Long id);
    List<InventoryResponse> getAllInventories();
    InventoryResponse updateInventory(
            Long id,
            InventoryRequest request);
    void deleteInventory(Long id);
}
