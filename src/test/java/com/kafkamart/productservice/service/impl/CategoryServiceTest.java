package com.kafkamart.productservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kafkamart.productservice.dto.request.CategoryRequest;
import com.kafkamart.productservice.dto.response.CategoryResponse;
import com.kafkamart.productservice.entity.Category;
import com.kafkamart.productservice.exception.CategoryNotFoundException;
import com.kafkamart.productservice.mapper.CategoryMapper;
import com.kafkamart.productservice.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_ShouldReturnCategoryResponse() {

        CategoryRequest request = new CategoryRequest();
        Category category = new Category();
        Category savedCategory = new Category();
        CategoryResponse response = new CategoryResponse();

        when(categoryMapper.toEntity(request)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryMapper.toResponse(savedCategory)).thenReturn(response);

        CategoryResponse result = categoryService.createCategory(request);

        assertNotNull(result);

        verify(categoryMapper).toEntity(request);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toResponse(savedCategory);
    }

    @Test
    void getCategoryById_ShouldReturnCategoryResponse() {

        Long categoryId = 1L;

        Category category = new Category();
        CategoryResponse response = new CategoryResponse();

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));

        when(categoryMapper.toResponse(category))
                .thenReturn(response);

        CategoryResponse result =
                categoryService.getCategoryById(categoryId);

        assertNotNull(result);

        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).toResponse(category);
    }

    @Test
    void getCategoryById_ShouldThrowException_WhenCategoryNotFound() {

        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategoryById(categoryId)
        );

        verify(categoryRepository).findById(categoryId);
    }

    @Test
    void getAllCategories_ShouldReturnCategoryList() {

        Category category1 = new Category();
        Category category2 = new Category();

        CategoryResponse response1 = new CategoryResponse();
        CategoryResponse response2 = new CategoryResponse();

        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(category1, category2));

        when(categoryMapper.toResponse(category1))
                .thenReturn(response1);

        when(categoryMapper.toResponse(category2))
                .thenReturn(response2);

        List<CategoryResponse> result =
                categoryService.getAllCategories();

        assertEquals(2, result.size());

        verify(categoryRepository).findAll();
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() {

        Long categoryId = 1L;

        Category category = new Category();

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(category));

        doNothing().when(categoryRepository).delete(category);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).delete(category);
    }

    @Test
    void deleteCategory_ShouldThrowException_WhenCategoryNotFound() {

        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.deleteCategory(categoryId)
        );

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).delete(any());
    }
}