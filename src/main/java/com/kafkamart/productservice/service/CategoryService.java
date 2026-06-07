package com.kafkamart.productservice.service;

import java.util.List;

import com.kafkamart.productservice.dto.request.CategoryRequest;
import com.kafkamart.productservice.dto.response.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    void deleteCategory(Long id);
}