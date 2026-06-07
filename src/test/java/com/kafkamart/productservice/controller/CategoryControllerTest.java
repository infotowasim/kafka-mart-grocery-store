package com.kafkamart.productservice.controller;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkamart.productservice.dto.request.CategoryRequest;
import com.kafkamart.productservice.dto.response.CategoryResponse;
import com.kafkamart.productservice.security.JwtAuthenticationFilter;
import com.kafkamart.productservice.security.JwtService;
import com.kafkamart.productservice.service.CategoryService;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategory_ShouldReturnCreatedCategory() throws Exception {

        CategoryRequest request = new CategoryRequest();
        request.setCategoryName("Fruits");

        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setCategoryName("Fruits");

        when(categoryService.createCategory(any(CategoryRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoryName").value("Fruits"));
    }

    @Test
    void getCategoryById_ShouldReturnCategory() throws Exception {

        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setCategoryName("Fruits");

        when(categoryService.getCategoryById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.categoryName").value("Fruits"));
    }

    @Test
    void getAllCategories_ShouldReturnCategoryList() throws Exception {

        CategoryResponse category1 = new CategoryResponse();
        category1.setId(1L);
        category1.setCategoryName("Fruits");

        CategoryResponse category2 = new CategoryResponse();
        category2.setId(2L);
        category2.setCategoryName("Vegetables");

        List<CategoryResponse> categories =
                Arrays.asList(category1, category2);

        when(categoryService.getAllCategories())
                .thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void deleteCategory_ShouldReturnSuccessMessage() throws Exception {

        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Category Deleted Successfully"));
    }
}