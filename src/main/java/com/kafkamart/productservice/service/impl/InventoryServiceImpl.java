package com.kafkamart.productservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kafkamart.productservice.dto.request.InventoryRequest;
import com.kafkamart.productservice.dto.response.InventoryResponse;
import com.kafkamart.productservice.entity.Inventory;
import com.kafkamart.productservice.entity.Product;
import com.kafkamart.productservice.repository.InventoryRepository;
import com.kafkamart.productservice.repository.ProductRepository;
import com.kafkamart.productservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public InventoryResponse createInventory(
            InventoryRequest request) {

        System.out.println("ProductId = " + request.getProductId());

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product Not Found"));

        Inventory inventory = new Inventory();

        inventory.setQuantity(request.getQuantity());
        inventory.setReservedQuantity(
                request.getReservedQuantity());
        inventory.setProduct(product);

        Inventory savedInventory =
                inventoryRepository.save(inventory);

        InventoryResponse response =
                new InventoryResponse();

        response.setId(savedInventory.getId());
        response.setQuantity(
                savedInventory.getQuantity());
        response.setReservedQuantity(
                savedInventory.getReservedQuantity());
        response.setProductName(
                product.getProductName());

        return response;
    }

    @Override
    public InventoryResponse getInventoryById(Long id) {

        Inventory inventory = inventoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Inventory Not Found"));

        InventoryResponse response =
                new InventoryResponse();

        response.setId(inventory.getId());
        response.setQuantity(
                inventory.getQuantity());
        response.setReservedQuantity(
                inventory.getReservedQuantity());
        response.setProductName(
                inventory.getProduct().getProductName());

        return response;
    }
    @Override
    public List<InventoryResponse> getAllInventories() {
    	

        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> {

                    InventoryResponse response =
                            new InventoryResponse();

                    response.setId(inventory.getId());
                    response.setQuantity(
                            inventory.getQuantity());

                    response.setReservedQuantity(
                            inventory.getReservedQuantity());

                    if (inventory.getProduct() != null) {

                        response.setProductName(
                                inventory.getProduct()
                                         .getProductName());
                    }

                    return response;
                })
                .toList();
    }
    @Override
    public InventoryResponse updateInventory(
            Long id,
            InventoryRequest request) {

        Inventory inventory = inventoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Inventory Not Found"));

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product Not Found"));

        inventory.setQuantity(request.getQuantity());
        inventory.setReservedQuantity(
                request.getReservedQuantity());
        inventory.setProduct(product);

        Inventory updatedInventory =
                inventoryRepository.save(inventory);

        InventoryResponse response =
                new InventoryResponse();

        response.setId(updatedInventory.getId());
        response.setQuantity(
                updatedInventory.getQuantity());
        response.setReservedQuantity(
                updatedInventory.getReservedQuantity());
        response.setProductName(
                product.getProductName());

        return response;
    }
    @Override
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Inventory Not Found"));

        inventoryRepository.delete(inventory);
    }
}