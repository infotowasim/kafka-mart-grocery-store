package com.kafkamart.productservice.service;

import java.util.List;

import com.kafkamart.productservice.dto.request.ProductRequest;
import com.kafkamart.productservice.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    List<ProductResponse> getAllProducts();

    void deleteProduct(Long id);

    List<ProductResponse> searchProductsByName(String keyword);

    List<ProductResponse> searchProductsByCategory(Long categoryId);
}