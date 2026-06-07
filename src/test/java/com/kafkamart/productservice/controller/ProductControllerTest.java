package com.kafkamart.productservice.controller;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkamart.productservice.dto.request.ProductRequest;
import com.kafkamart.productservice.dto.response.ProductResponse;
import com.kafkamart.productservice.security.JwtAuthenticationFilter;
import com.kafkamart.productservice.security.JwtService;
import com.kafkamart.productservice.service.ProductService;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {

        ProductRequest request = new ProductRequest();

        request.setProductName("iPhone 16");
        request.setDescription("Apple Mobile");
        request.setPrice(50000.0);
        request.setStockQuantity(100);
        request.setSkuCode("IPH-001");
        request.setBrand("Apple");
        request.setImageUrl("iphone.jpg");
        request.setCategoryId(1L);

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setProductName("iPhone 16");

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/products")
                .with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    
    }

    @Test
    void getProductById_ShouldReturnProduct() throws Exception {

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setProductName("Apple");

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/products/1")
                .with(user("customer").roles("CUSTOMER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Apple"));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {

        ProductRequest request = new ProductRequest();

        request.setProductName("Updated iPhone");
        request.setDescription("Updated Apple Mobile");
        request.setPrice(60000.0);
        request.setStockQuantity(50);
        request.setSkuCode("IPH-002");
        request.setBrand("Apple");
        request.setImageUrl("updated-iphone.jpg");
        request.setCategoryId(1L);

        ProductResponse response = new ProductResponse();
        response.setId(1L);
        response.setProductName("Updated iPhone");

        when(productService.updateProduct(anyLong(),
                any(ProductRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/products/1")
                .with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    
    }

    @Test
    void getAllProducts_ShouldReturnProductList() throws Exception {

        ProductResponse product1 = new ProductResponse();
        product1.setId(1L);
        product1.setProductName("Apple");

        ProductResponse product2 = new ProductResponse();
        product2.setId(2L);
        product2.setProductName("Banana");

        List<ProductResponse> products =
                Arrays.asList(product1, product2);

        when(productService.getAllProducts())
                .thenReturn(products);

        mockMvc.perform(get("/api/products")
                .with(user("customer").roles("CUSTOMER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void deleteProduct_ShouldReturnSuccessMessage() throws Exception {

        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1")
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Product Deleted Successfully"));
    }
}