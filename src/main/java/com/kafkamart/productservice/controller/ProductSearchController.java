package com.kafkamart.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.kafkamart.productservice.dto.response.ProductResponse;
import com.kafkamart.productservice.service.ProductService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/products/search")
public class ProductSearchController {

    private final ProductService productService;

    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Search products by keyword
     * Example:
     * GET /api/products/search?keyword=milk
     */
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam
            @NotBlank(message = "Keyword is required")
            String keyword) {

        return ResponseEntity.ok(
                productService.searchProductsByName(keyword.trim()));
    }

    /**
     * Search products by category
     * Example:
     * GET /api/products/search/category/1
     */
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> searchByCategory(
            @PathVariable
            @Min(value = 1, message = "Category Id must be greater than 0")
            Long categoryId) {

        return ResponseEntity.ok(
                productService.searchProductsByCategory(categoryId));
    }
    
}