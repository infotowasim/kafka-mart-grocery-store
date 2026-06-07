package com.kafkamart.productservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kafkamart.productservice.dto.request.ProductRequest;
import com.kafkamart.productservice.dto.response.ProductResponse;
import com.kafkamart.productservice.entity.Category;
import com.kafkamart.productservice.entity.Product;
import com.kafkamart.productservice.event.payload.ProductCreatedEvent;
import com.kafkamart.productservice.event.producer.ProductEventProducer;
import com.kafkamart.productservice.exception.CategoryNotFoundException;
import com.kafkamart.productservice.exception.ProductNotFoundException;
import com.kafkamart.productservice.mapper.ProductMapper;
import com.kafkamart.productservice.repository.CategoryRepository;
import com.kafkamart.productservice.repository.ProductRepository;
import com.kafkamart.productservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log =
            LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductEventProducer productEventProducer;
    private final ProductMapper productMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductEventProducer productEventProducer,
            ProductMapper productMapper) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productEventProducer = productEventProducer;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        log.info("Creating Product : {}", request.getProductName());

        Category category = categoryRepository.findById(
                request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category Not Found"));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        ProductCreatedEvent event = new ProductCreatedEvent();
        event.setProductId(savedProduct.getId());
        event.setProductName(savedProduct.getProductName());
        event.setPrice(savedProduct.getPrice());
        event.setStockQuantity(savedProduct.getStockQuantity());
        event.setCategoryId(savedProduct.getCategory().getId());
        
        productEventProducer.publishProductCreatedEvent(event);

        log.info("Product Created Event Published To Kafka");

        log.info(
                "Product Created Successfully with Id : {}",
                savedProduct.getId());

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {

        log.info("Fetching Product with Id : {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id : " + id));

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        log.info("Updating Product with Id : {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product Not Found"));

        Category category = categoryRepository.findById(
                request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category Not Found"));

        productMapper.updateEntity(product, request);
        product.setCategory(category);

        Product updatedProduct =
                productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
    @Override
    public List<ProductResponse> searchProductsByName(String keyword) {

        return productRepository
                .findByProductNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> searchProductsByCategory(Long categoryId) {

        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {

        log.info("Deleting Product with Id : {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product Not Found"));

        productRepository.delete(product);
    }
}