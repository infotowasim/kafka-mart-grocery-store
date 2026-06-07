package com.kafkamart.productservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafkamart.productservice.entity.Product;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByProductNameContainingIgnoreCase(
            String productName);

    List<Product> findByCategoryId(Long categoryId);
}