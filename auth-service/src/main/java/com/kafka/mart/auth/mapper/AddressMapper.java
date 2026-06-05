package com.kafka.mart.auth.mapper;

import com.kafka.mart.auth.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(Address address);
}
