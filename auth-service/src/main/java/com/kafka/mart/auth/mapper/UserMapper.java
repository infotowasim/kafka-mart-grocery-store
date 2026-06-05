package com.kafka.mart.auth.mapper;


import com.kafka.mart.auth.dto.request.RegisterRequest;
import com.kafka.mart.auth.dto.response.UserResponse;
import com.kafka.mart.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "role", source = "role.name")
    UserResponse toResponse(User user);

}
