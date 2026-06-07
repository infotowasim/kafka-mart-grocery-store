package com.kafkamart.productservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkamart.productservice.dto.response.ProductResponse;
import com.kafkamart.productservice.security.JwtAuthenticationFilter;
import com.kafkamart.productservice.security.JwtService;
import com.kafkamart.productservice.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductSearchController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductSearchControllerTest {

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
    void searchProducts_ShouldReturnProductList() throws Exception {

        ProductResponse product1 = new ProductResponse();
        product1.setId(1L);
        product1.setProductName("Milk");

        ProductResponse product2 = new ProductResponse();
        product2.setId(2L);
        product2.setProductName("Milk Powder");

        List<ProductResponse> products =
                Arrays.asList(product1, product2);

        when(productService.searchProductsByName("milk"))
                .thenReturn(products);

        mockMvc.perform(
                get("/api/products/search")
                        .param("keyword", "milk")
                        .with(user("customer")
                                .roles("CUSTOMER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].productName")
                        .value("Milk"))
                .andExpect(jsonPath("$[1].productName")
                        .value("Milk Powder"));
    }

    @Test
    void searchByCategory_ShouldReturnProductList()
            throws Exception {

        ProductResponse product1 = new ProductResponse();
        product1.setId(1L);
        product1.setProductName("Milk");

        ProductResponse product2 = new ProductResponse();
        product2.setId(2L);
        product2.setProductName("Curd");

        List<ProductResponse> products =
                Arrays.asList(product1, product2);

        when(productService.searchProductsByCategory(1L))
                .thenReturn(products);

        mockMvc.perform(
                get("/api/products/search/category/1")
                        .with(user("customer")
                                .roles("CUSTOMER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].productName")
                        .value("Milk"))
                .andExpect(jsonPath("$[1].productName")
                        .value("Curd"));
    }

    @Test
    void searchProducts_WithBlankKeyword_ShouldReturnBadRequest()
            throws Exception {

        mockMvc.perform(
                get("/api/products/search")
                        .param("keyword", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchByCategory_WithInvalidId_ShouldReturnBadRequest()
            throws Exception {

        mockMvc.perform(
                get("/api/products/search/category/0"))
                .andExpect(status().isBadRequest());
    }
}