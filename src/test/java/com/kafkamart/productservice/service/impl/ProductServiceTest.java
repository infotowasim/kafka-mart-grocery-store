package com.kafkamart.productservice.service.impl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.kafkamart.productservice.dto.request.ProductRequest;
import com.kafkamart.productservice.dto.response.ProductResponse;
import com.kafkamart.productservice.entity.Category;
import com.kafkamart.productservice.entity.Product;
import com.kafkamart.productservice.event.producer.ProductEventProducer;
import com.kafkamart.productservice.exception.CategoryNotFoundException;
import com.kafkamart.productservice.exception.ProductNotFoundException;
import com.kafkamart.productservice.mapper.ProductMapper;
import com.kafkamart.productservice.repository.CategoryRepository;
import com.kafkamart.productservice.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductEventProducer productEventProducer;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_ShouldReturnProductResponse() {

        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        Product product = new Product();

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setCategory(category);

        ProductResponse response = new ProductResponse();

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(productMapper.toEntity(request))
                .thenReturn(product);

        when(productRepository.save(product))
                .thenReturn(savedProduct);

        when(productMapper.toResponse(savedProduct))
                .thenReturn(response);

        ProductResponse result =
                productService.createProduct(request);

        assertNotNull(result);

        verify(categoryRepository).findById(1L);
        verify(productRepository).save(product);
        verify(productEventProducer)
                .publishProductCreatedEvent(any());
    }

    @Test
    void createProduct_ShouldThrowException_WhenCategoryNotFound() {

        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> productService.createProduct(request));

        verify(productRepository, never()).save(any());
    }

    @Test
    void getProductById_ShouldReturnProduct() {

        Long productId = 1L;

        Product product = new Product();
        ProductResponse response = new ProductResponse();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result =
                productService.getProductById(productId);

        assertNotNull(result);

        verify(productRepository).findById(productId);
        verify(productMapper).toResponse(product);
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(1L));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {

        Long productId = 1L;

        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);

        Product product = new Product();

        Category category = new Category();
        category.setId(1L);

        ProductResponse response = new ProductResponse();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(productRepository.save(product))
                .thenReturn(product);

        when(productMapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result =
                productService.updateProduct(productId, request);

        assertNotNull(result);

        verify(productMapper)
                .updateEntity(product, request);

        verify(productRepository)
                .save(product);
    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {

        ProductRequest request = new ProductRequest();

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.updateProduct(1L, request));
    }

    @Test
    void getAllProducts_ShouldReturnProductList() {

        Product product1 = new Product();
        Product product2 = new Product();

        ProductResponse response1 = new ProductResponse();
        ProductResponse response2 = new ProductResponse();

        when(productRepository.findAll())
                .thenReturn(Arrays.asList(product1, product2));

        when(productMapper.toResponse(product1))
                .thenReturn(response1);

        when(productMapper.toResponse(product2))
                .thenReturn(response2);

        List<ProductResponse> result =
                productService.getAllProducts();

        assertEquals(2, result.size());

        verify(productRepository).findAll();
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {

        Product product = new Product();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        doNothing().when(productRepository)
                .delete(product);

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.deleteProduct(1L));

        verify(productRepository, never())
                .delete(any());
    }
}
