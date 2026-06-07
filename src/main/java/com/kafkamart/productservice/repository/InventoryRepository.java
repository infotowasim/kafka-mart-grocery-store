package com.kafkamart.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafkamart.productservice.entity.Inventory;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {
}