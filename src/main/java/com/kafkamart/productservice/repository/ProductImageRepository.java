package com.kafkamart.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kafkamart.productservice.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
