package com.kafkamart.productservice.service.impl;


import com.kafkamart.productservice.mapper.CategoryMapper;
import com.kafkamart.productservice.dto.request.CategoryRequest;
import com.kafkamart.productservice.dto.response.CategoryResponse;
import com.kafkamart.productservice.entity.Category;
import com.kafkamart.productservice.exception.CategoryNotFoundException;
import com.kafkamart.productservice.repository.CategoryRepository;
import com.kafkamart.productservice.service.CategoryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id : " + id));

        return categoryMapper.toResponse(category);
    }
    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id : " + id));

        categoryRepository.delete(category);
    }
}