package com.kafkamart.productservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kafkamart.productservice.dto.request.CategoryRequest;
import com.kafkamart.productservice.dto.response.CategoryResponse;
import com.kafkamart.productservice.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
}